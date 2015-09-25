package com.g.seed.autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

import org.apache.http.NameValuePair;

import com.g.seed.textresolver.EL;
import com.g.seed.textresolver.GText;
import com.g.seed.view.IExtendedAttributeOwner;
import com.g.seed.view.IFormElement;
import com.g.seed.view.IGTextAble;

import android.app.Activity;
import android.content.Context;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewManager {
	private GIntent gintent;
	private EL el;
	
	
	/**
	 * bean 的渲染后的spanned缓存。
	 * 此缓存是针对于每一个bean进行缓存而不是针对GTextAble，GTextAble只是提供Expression渲染模板。
	 * 使用缓存时需要以bean作为键来获取缓存，取到缓存后再依次从ViewGroup中的GTextAble取得cacheID作为键从缓存中获取对应的spanned。
	 * */
	/* package */ static WeakHashMap<Object, HashMap<Object, TextAbleR>> textAbleCache = new WeakHashMap<Object, HashMap<Object, TextAbleR>>();
	
	/** 
	 * 缓存ID。
	 * 在GTextAble被创建时便会给它添加一个动态属性”缓存ID“，
	 * 缓存ID在其所处的容器中是唯一的，而相对于其容器的其他同类对象中的另一个自己则是一样的。
	 * GTextAble的内容缓存以此ID做为键，在usecache的时候从GTextAble中取得动态属性”缓存ID“，再以此ID取得缓存的内容。
	 * */
	public static final String cacheID = "cacheID";
	
	public ViewManager() {}
	
	public ViewManager(GIntent gintent) {
		this.gintent = gintent;
		createEL();
	}
	
	public ViewManager(GIntent gintent, EL el) {
		this.gintent = gintent;
		this.el = el;
		this.el.setParams(gintent.getParams());
	}
	
	public ViewManager(View view, Params params) {
		this.gintent = new GIntent(view);
		this.gintent.setParams(params);
		createEL();
	}
	
	public ViewManager(Context context, int resID, Params params) {
		this.gintent = new GIntent(context, resID);
		this.gintent.setParams(params);
		createEL();
	}
	
	private EL createEL() {
		Params params = this.gintent.getParams();
		return (this.el = new EL(params));
	}
	
	private static ExtendedAttribute getExtendedAttribute(View view) {
		if (view instanceof IExtendedAttributeOwner) { return ((IExtendedAttributeOwner) view).getExtendedAttribute(); }
		if (view.getTag() != null || view.getTag() instanceof ExtendedAttribute) { return (ExtendedAttribute) view.getTag(); }
		return null;
	}
	
	private static void setExtendedAttribute(View view, ExtendedAttribute extendedAttribute) {
		if (view instanceof IExtendedAttributeOwner) {
			((IExtendedAttributeOwner) view).setExtendedAttribute(extendedAttribute);
		} else {
			view.setTag(extendedAttribute);
		}
	}
	
	public View flate() {
		ExtendedAttribute extendedAttribute = new ExtendedAttribute();
		View view = this.gintent.getTarget();
		int layoutID = view.getClass().getAnnotation(Autowired.class).value();
		if (layoutID != 0)
			getInFlater(extendedAttribute).inflate(layoutID, (ViewGroup) view);
		setExtendedAttribute(view, extendedAttribute);
		return eview(view);
	}
	
	public View create() {
		ExtendedAttribute extendedAttribute = new ExtendedAttribute();
		int resourceID = this.gintent.getTargetID().intValue();
		View view = getInFlater(extendedAttribute).inflate(resourceID, null);
		setExtendedAttribute(view, extendedAttribute);
		return eview(view);
	}
	
	private View eview(View view) {
		new Injector(view, this.el).execute();
		if (view instanceof ViewManagerCallBack)
			((ViewManagerCallBack) view).ready(this.gintent);
		return view;
	}
	
	public static void dataChange(View view, Object bean) {
		//		new Injector(view, bean).injectData();
		if (textAbleCache.containsKey(bean)) {
			usecache(view, bean);
		} else {
			jiexiAndCache(view, bean);
		}
	}
	
	private static void usecache(View view, Object bean) {
		HashMap<Object, TextAbleR> map = textAbleCache.get(bean);
		for (IGTextAble textAble : getExtendedAttribute(view).getTextAbles()) {
			TextAbleR textAbleR = map.get(textAble.getDA(cacheID));
			textAble.setText(textAbleR.getContent());
		}
	}
	
	private static void jiexiAndCache(View view, Object bean) {
		HashMap<Object, TextAbleR> map = new HashMap<Object, TextAbleR>();
		for (IGTextAble textAble : getExtendedAttribute(view).getTextAbles()) {
			final String eled = new EL(bean).exe(textAble.getExpression());
			final Spanned spanned = new GText(eled).exe();
			textAble.setText(spanned);
			map.put(textAble.getDA(cacheID), new TextAbleR(spanned, textAble.getExpression()));
		}
		textAbleCache.put(bean, map);
	}
	
	public static void updateCache(Object bean) {
		if (!textAbleCache.containsKey(bean)) { return; }
		HashMap<Object, TextAbleR> map = textAbleCache.get(bean);
		for (TextAbleR textAbleR : map.values()) {
			textAbleR.setContent(new GText(new EL(bean).exe(textAbleR.getExpression())).exe());
		}
	}
	
	public static boolean formCheck(Activity activity) {
		return formCheck(getRootView(activity));
	}
	
	public static boolean formCheck(View view) {
		for (IFormElement formElement : getExtendedAttribute(view).getFormElements()) {
			if (!formElement.check()) { return false; }
		}
		return true;
	}
	
	public static void buildPO(Activity activity, Object po) {
		buildPO(getRootView(activity), po);
	}
	
	public static Object buildPO(Activity activity, Class<?> clazz) {
		return buildPO(getRootView(activity), clazz);
	}
	
	public static List<NameValuePair> buildPairs(Activity activity) {
		return buildPairs(getRootView(activity));
	}
	
	private static View getRootView(Activity activity) {
		return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
	}
	
	public static List<NameValuePair> buildPairs(View view) {
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		for (IFormElement formElement : getExtendedAttribute(view).getFormElements()) {
			result.add(formElement.build());
		}
		return result;
	}
	
	public static Object buildPO(View view, Class<?> clazz) {
		Object result = null;
		try {
			result = clazz.newInstance();
			buildPO(view, result);
		} catch (Exception e) {
			Log.e("buildPO", "buildPO err:", e);
		}
		return result;
	}
	
	public static void buildPO(View view, Object po) {
		try {
			Class<?> clazz = po.getClass();
			for (IFormElement formElement : getExtendedAttribute(view).getFormElements()) {
				try {
					Field field = clazz.getDeclaredField(formElement.getName());
					field.setAccessible(true);
					field.set(po, formElement.value());
				} catch (NoSuchFieldException e) {
					Log.w("buildPO", "buildPO-NoSuchField:" + formElement.getName());
				}
			}
		} catch (Exception e) {
			Log.e("buildPO", "buildPO err:", e);
		}
	}
	
	public static void cache(Object key, IGTextAble textAble, TextAbleR textAbleR) {
		HashMap<Object, TextAbleR> map = textAbleCache.get(key);
		if (map == null) {
			map = new HashMap<Object, TextAbleR>();
			textAbleCache.put(key, map);
		}
		map.put(textAble.getDA(cacheID), textAbleR);
	}
	
	private LayoutInflater getInFlater(ExtendedAttribute extendedAttribute) {
		LayoutInflater original = LayoutInflater.from(this.gintent.getContext());
		LayoutInflater copy = original.cloneInContext(this.gintent.getContext());
		copy.setFactory(new GViewFactory(copy, this.el, extendedAttribute));
		return copy;
	}
}
