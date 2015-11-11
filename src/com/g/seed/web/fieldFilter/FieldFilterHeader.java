/**
 * 
 */
package com.g.seed.web.fieldFilter;

import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.web.Header;
import com.g.seed.web.service.IParamPository;

public class FieldFilterHeader implements IFieldFilter {
	public FieldFilterHeader(IParamPository paramPository) {
		this.paramPository = paramPository;
	}
	private IParamPository paramPository;
	
	@Override
	public boolean exe(FieldFiltrateInfo fi) throws Exception {
		if (fi.field.isAnnotationPresent(Header.class)) {
			paramPository.addHeader(fi.name, fi.value);
			return false;
		}
		return true;
	}
}
