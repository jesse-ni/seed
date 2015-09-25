package com.g.seed.autowired;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Params extends HashMap<String, Object>
{
	private static final long serialVersionUID = -6502587425187963888L;
	public static final String beanKey = "bean";
	
	public Params(){
	
	}
	
	public Params(Object bean){
		setBean(bean);
	}
	
	public Params(Bundle bundle) {
		putAll(bundle);
	}
	
	public static Map<String, Object> dismember(Object bean)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Field[] arrayOfField;
			int j = (arrayOfField = bean.getClass().getDeclaredFields()).length;
			for (int i = 0; i < j; ++i) {
				Field field = arrayOfField[i];
				field.setAccessible(true);
				result.put(field.getName(), field.get(bean));
			}
		} catch (Exception e) {
			Log.e(Params.class.getName(), e.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public void putAll(Bundle bundle)
	{
		if (bundle == null)
			return;	
		try {
			Class<?> clazz = bundle.getClass();
			Method methodUnparcel = clazz.getDeclaredMethod("unparcel");
			methodUnparcel.setAccessible(true);
			methodUnparcel.invoke(bundle);
			Field declaredField = clazz.getDeclaredField("mMap");
			declaredField.setAccessible(true);
			putAll((Map<String, Object>)declaredField.get(bundle));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	public void setBean(Object bean) {
		put(beanKey, bean);
		if (bean != null)
			putAll(dismember(bean));
	}
	
	public Object getBean() {
		return get(beanKey);
	}
	
}
