/**
 * 
 */
package com.g.seed.web.fieldFilter;

import java.util.Map;
import java.util.Map.Entry;

import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.web.service.IParamPository;

public class FieldFilterMap implements IFieldFilter {
	public FieldFilterMap(IParamPository paramPository) {
		this.paramPository = paramPository;
	}
	
	private IParamPository paramPository;
	
	@Override
	public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
			NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
		if (fi.value instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) fi.value;
			for(Entry<String, Object> entry:map.entrySet()){
				paramPository.add(entry.getKey(), entry.getValue());
			}
			return false;
		}
		return true;
	}
}