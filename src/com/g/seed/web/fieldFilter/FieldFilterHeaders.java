/**
 * 
 */
package com.g.seed.web.fieldFilter;

import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.web.Headers;
import com.g.seed.web.POTool;
import com.g.seed.web.service.IParamPository;

public class FieldFilterHeaders implements IFieldFilter {
	public FieldFilterHeaders(POTool poTool, IParamPository paramPository) {
		this.paramPository = paramPository;
		this.poTool = poTool;
	}
	
	private IParamPository paramPository;
	private POTool poTool;
	
	@Override
	public boolean exe(FieldFiltrateInfo fi) throws Exception {
		if (fi.field.isAnnotationPresent(Headers.class)) {
			poTool.changeParam2(new IParamPository() {
				
				@Override
				public void addHeader(String name, Object value) {
					paramPository.addHeader(name, value);
				}
				
				@Override
				public void add(String name, Object value) {
					paramPository.addHeader(name, value);
				}
			}, fi.value);
			return false;
		}
		return true;
	}
}
