/**
 * 
 */
package com.g.seed.web.fieldFilter;

import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.web.Name;

public class FieldFilterName implements IFieldFilter {
	@Override
	public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
			NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
		if (fi.field.isAnnotationPresent(Name.class)) {
			fi.name = fi.field.getAnnotation(Name.class).value();
		}
		return true;
	}
}