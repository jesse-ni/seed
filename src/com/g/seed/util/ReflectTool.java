package com.g.seed.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.g.seed.web.exception.ReflectToolException;

import android.util.Log;

/**
 * @ClassName: ReflectTool
 * @author zhigeng.ni
 * @date 2015年5月6日 上午11:53:29
 * @Description: TODO (描述作用)
 * 				
 */
public class ReflectTool {
	private List<IFieldFilter> filters = new ArrayList<IFieldFilter>();
//	private List<IFieldFilter> tempfilters = new ArrayList<IFieldFilter>();
	private List<IClazzFilter> clazzFilters = new ArrayList<IClazzFilter>();
	private static final String TAG = ReflectTool.class.getName();
	
	public ReflectTool() {}
	
	public ReflectTool(IFieldFilter... f) {
		this.filters.addAll(Arrays.asList(f));
	}

	public ReflectTool(List<IFieldFilter> f) {
		this.filters.addAll(f);
	}
	
	public void catchAttr(Object obj, IFieldFilter... f) {
		if (obj == null)
			throw new ReflectToolException("the object want to filtered can not be null");
		for (Class<?> clazz = obj.getClass(); checkClazz(clazz); clazz = clazz.getSuperclass()) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					Object value = field.get(obj);
					FieldFiltrateInfo fi = new FieldFiltrateInfo(clazz, obj, field, field.getName(), value);
					if (fieldFiltrate(fi, filters))
						fieldFiltrate(fi, Arrays.asList(f));
				} catch (Exception e) {
					Log.e(TAG, "catchAttr fail: ", e);
				}
			}
		}
	}
	
	public List<IFieldFilter> getFilters() {
		return filters;
	}
	
	public void setFilters(List<IFieldFilter> filters) {
		this.filters = filters;
	}
	
	private boolean checkClazz(Class<?> clazz) {
		return ((clazz != Object.class) && (clazzFiltrate(clazz)));
	}
	
	public void addClazzFilter(IClazzFilter f) {
		this.clazzFilters.add(f);
	}
	
	public boolean clazzFiltrate(Class<?> clazz) {
		for (IClazzFilter unit : clazzFilters)
			if (!unit.exe(clazz))
				return false;
		return true;
	}
	
	/**
	 * @Description: TODO
	 *               (对一个FiltrateInfo执行多个过滤器，如果遇到过滤器返回false则终止本次对FiltrateInfo的过滤
	 *               ，后面的过滤器将不会被执行)
	 * 
	 */
	public boolean fieldFiltrate(FieldFiltrateInfo fi, List<IFieldFilter> filters) throws Exception {
		for (IFieldFilter filter : filters)
			if (!filter.exe(fi))
				return false;
		return true;
	}
	
	public void catchMethod(Object obj, IMethodFilter... filters) {
		for (Class<?> clazz = obj.getClass(); checkClazz(clazz); clazz = clazz.getSuperclass()) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				method.setAccessible(true);
				for (IMethodFilter filter : filters) {
					try {
						if (!filter.exe(new MethodFiltrateInfo(method))) {
							break;
						}
					} catch (Exception e) {
						Log.e(TAG, "catchAttr fail: " + e.toString());
					}
				}
			}
		}
	}
	
	/**
	 * @ClassName: FiltrateInfo
	 * @author zhigeng.ni
	 * @date 2015年5月6日 上午11:53:42
	 * @Description: TODO (描述作用)
	 * 				
	 */
	public static class FieldFiltrateInfo {
		public final Class<?> clazz;
		public final Object obj;
		public final Field field;
		public String name;
		public Object value;
		
		public Map<String, Object> datas = new HashMap<String, Object>();
		
		public FieldFiltrateInfo(Class<?> clazz, Object obj, Field field, String name, Object value) {
			this.clazz = clazz;
			this.obj = obj;
			this.field = field;
			this.name = name;
			this.value = value;
		}
		
		public void put(String key, Object value) {
			this.datas.put(key, value);
		}
		
		public Object get(String key) {
			return this.datas.get(key);
		}
	}
	
	public static class MethodFiltrateInfo {
		public Method method;
		
		public MethodFiltrateInfo(Method method) {
			super();
			this.method = method;
		}
		
	}
	
	public static abstract interface IClazzFilter {
		public abstract boolean exe(Class<?> paramClass);
	}
	
	public static abstract interface IFieldFilter {
		/**
		 * @Description: TODO (返回false则终止本次对FiltrateInfo的过滤，后面的过滤器将不会被执行)
		 */
		public abstract boolean exe(ReflectTool.FieldFiltrateInfo paramFiltrateInfo) throws Exception;
	}
	
	public static abstract interface IMethodFilter {
		/**
		 * @Description: TODO (返回false则终止本次对FiltrateInfo的过滤，后面的过滤器将不会被执行)
		 */
		public abstract boolean exe(ReflectTool.MethodFiltrateInfo paramFiltrateInfo) throws Exception;
	}
}
