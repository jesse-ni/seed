package com.g.seed.autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.g.seed.textresolver.EL;
import com.g.seed.util.ReflectTool;
import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.util.ReflectTool.IMethodFilter;
import com.g.seed.util.ReflectTool.MethodFiltrateInfo;
import com.g.seed.util.SrcDynamicAccess;

import android.app.Activity;
import android.util.Log;
import android.view.View;

public class Injector
{
	private static final String TAG = Injector.class.getName();
	private TargetProxy proxy;
	private EL el;
	private ReflectTool reflectTool;
	
	public Injector(Activity target, Params params)
	{
		this(target, new EL(params));
	}
	
	public Injector(Activity target, EL el) {
		this(TargetProxyFactory.proxy(target), el);
	}
	
	public Injector(View target, Params params) {
		this(target, new EL(params));
	}

	public Injector(View target, Object data) {
		this(target, new EL(new Params(data)));
	}
	
	public Injector(View target, EL el) {
		this(TargetProxyFactory.proxy(target), el);
	}
	
	public Injector(TargetProxy proxy, EL el)
	{
		this.reflectTool = new ReflectTool();
		
		this.proxy = proxy;
		this.el = el;
		
		this.reflectTool.addClazzFilter(new ReflectTool.IClazzFilter()
		{
			@Override
			public boolean exe(Class<?> clazz) {
				Log.i("", "Class:" + clazz.getName() + " >> " + clazz.isAnnotationPresent(Autowired.class));
				return clazz.isAnnotationPresent(Autowired.class);
			}
		});
	}
	
	public void injectData(){
		Log.i(TAG, "---> " + this.proxy.real().getClass().getName());
		reflectTool.catchAttr(this.proxy.real(), new IFieldFilter() {
			
			@Override
			public boolean exe(FieldFiltrateInfo filtrateInfo) throws IllegalAccessException, IllegalArgumentException,
					NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
				if (filtrateInfo.field.isAnnotationPresent(Inject.class)) {
					injectDataToField(filtrateInfo);
				}
				return true;
			}
			
		});
	}
	
	public void execute() {
		Log.i(TAG, "---> " + this.proxy.real().getClass().getName());
		reflectTool.catchAttr(this.proxy.real(), new IFieldFilter() {
			
			@Override
			public boolean exe(FieldFiltrateInfo filtrateInfo) throws IllegalAccessException, IllegalArgumentException,
					NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
				injectValue(filtrateInfo);
				bingOnClickListener(filtrateInfo.clazz, filtrateInfo.obj, filtrateInfo.field);
				return true;
			}
			
		});
		reflectTool.catchMethod(this.proxy.real(), new IMethodFilter(){

			@Override
			public boolean exe(final MethodFiltrateInfo info) throws Exception {
				if(info.method.isAnnotationPresent(OnClick2.class)){
					OnClick2 oc2 = info.method.getAnnotation(OnClick2.class);
					final int[] idArray = oc2.value();
					for (int id : idArray) {
						bindMethod(proxy.real(), proxy.findViewById(id), info.method);
					}
				}
				return true;
			}
			
		});
		if (this.proxy.real() instanceof InjectFinishCallBack)
			((InjectFinishCallBack) this.proxy.real()).ready();
	}
	
	private void injectValue(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
			NoSuchFieldException, ClassNotFoundException
	{
		fi.field.setAccessible(true);
		if (fi.field.isAnnotationPresent(InjectView.class)) {
			injectViewToField(fi);
		} else if (fi.field.isAnnotationPresent(Inject.class)) {
			injectDataToField(fi);
		}
		
		Object value = fi.field.get(this.proxy.real());
		String strValue;
		if (value == null) {
			strValue = "@null";
		} else {
			strValue = isPrimitive(fi.field.getType()) ? String.valueOf(value) : "@" + fi.field.getType().getSimpleName();
		}
		Log.i(TAG, "  -" + fi.field.getName() + " = " + strValue);
	}

	private void injectViewToField(FieldFiltrateInfo fi) throws IllegalAccessException {
		InjectView injectView = fi.field.getAnnotation(InjectView.class);
		Integer id = getInjectViewID(injectView, fi.field);
		if (id != null)
			fi.field.set(this.proxy.real(), getView(id, injectView.create()));
	}

	private void injectDataToField(FieldFiltrateInfo fi) throws IllegalAccessException {
		Inject inject = fi.field.getAnnotation(Inject.class);
		if (!inject.value().equals("")) {
			if (isPrimitive(fi.field.getType())) {
				String val = this.el.exe(inject.value());
				fi.field.set(this.proxy.real(), valueAdapt(fi.field, val));
			} else {
				fi.field.set(this.proxy.real(), this.el.analyze(inject.value()));
			}
		} else {
			fi.field.set(this.proxy.real(), this.el.analyze2(fi.field.getName()));
		}
	}
	
	private boolean isPrimitive(Class<?> clz) {
		try {
			return ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return clz.equals(String.class);
		}
	}
	
	private Object valueAdapt(Field f, String val) {
		Class<?> type = f.getType();
		if (type.equals(Integer.class) || type == int.class)
			return Integer.parseInt((String) val);
		if (type.equals(Float.class) || type == float.class)
			return Float.parseFloat(val);
		if (type.equals(Double.class) || type == double.class)
			return Double.parseDouble(val);
		if (type.equals(Long.class) || type == long.class)
			return Long.parseLong(val);
		if (type.equals(Boolean.class) || type == boolean.class)
			return Boolean.parseBoolean(val);
		if (type.equals(Byte.class) || type == byte.class)
			return Byte.parseByte(val);
		if (type.equals(Character.class) || type == char.class)
			return val.toCharArray()[0];
		return val;
	}
	
	private Integer getInjectViewID(InjectView injectView, Field field)
	{
		if (injectView.value() > 0)
			return injectView.value();
		if (injectView.create())
			return SrcDynamicAccess.getLayout(field.getName());
		
		return SrcDynamicAccess.getID(field.getName());
	}
	
	private View getView(int id, boolean create)
	{
		if (create) {
			GIntent gintent = new GIntent(this.proxy.getContext(), id);
			return new ViewManager(gintent, this.el).create();
		}
		return this.proxy.findViewById(id);
	}
	
	private void bingOnClickListener(Class<?> clazz, Object obj, Field field)
			throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException
	{
		if (field.isAnnotationPresent(OnClick.class)) {
			OnClick annOnClick = field.getAnnotation(OnClick.class);
			field.setAccessible(true);
			Object fieldValue = field.get(this.proxy.real());
			if (!(fieldValue instanceof View))
				throw new RuntimeException("bingOnClickListener on non-view!!!");
			View view = (View) fieldValue;
			if (!(annOnClick.value().equals(""))) {
				final Method method = clazz.getDeclaredMethod(annOnClick.value(), View.class);
				method.setAccessible(true);
				bindMethod(obj, view, method);
			}
		}
	}
	
	private void bindMethod(final Object obj, View view, final Method method) {
		view.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				try {
					method.invoke(obj, view);
				} catch (Exception e) {
					throw new InjectException(e);
				}
			}
		});
	}
}
