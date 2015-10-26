package com.g.seed.autowired;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;

public class Params extends HashMap<String, Object>
{
	private static final long serialVersionUID = -6502587425187963888L;
	public static final String beanKey = "bean";
	public static final String primitiveKey = "data";
	
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
	
	public void putAll(Bundle bundle)
	{
		if (bundle == null)
			return;	
		try {
			for(String key:bundle.keySet()){
				put(key, bundle.get(key));
			}
		}  catch (Exception e) {
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
