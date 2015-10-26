/**
 * 
 */
package com.g.seed.web.fieldFilter;

import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.web.Component;
import com.g.seed.web.POTool;
import com.g.seed.web.service.IParamPository;

public class FieldFilterComponent implements IFieldFilter {
	public FieldFilterComponent(POTool poTool, IParamPository paramPository) {
		this.paramPository = paramPository;
		this.poTool = poTool;
	}
	
	private IParamPository paramPository;
	private POTool poTool;
	
	@Override
	public boolean exe(FieldFiltrateInfo fi) throws Exception {
		if (fi.field.isAnnotationPresent(Component.class)) {
			poTool.changeParam2(paramPository, fi.value);
			return false;
		}
		return true;
	}
}
