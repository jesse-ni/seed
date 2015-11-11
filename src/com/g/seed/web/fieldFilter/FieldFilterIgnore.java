/**
 * 
 */
package com.g.seed.web.fieldFilter;

import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.web.Ignore;

public class FieldFilterIgnore implements IFieldFilter {
	@Override
	public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
			NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
		return !fi.field.isAnnotationPresent(Ignore.class);
	}
}
