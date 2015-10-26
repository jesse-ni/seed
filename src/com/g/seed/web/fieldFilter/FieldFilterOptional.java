/**
 * 
 */
package com.g.seed.web.fieldFilter;

import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.web.Optional;

import android.text.TextUtils;

public class FieldFilterOptional implements IFieldFilter {
	@Override
	public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
			NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
		if (fi.field.isAnnotationPresent(Optional.class)) {
			if (fi.field.getAnnotation(Optional.class).value()) {
				return fi.value != null;
			} else {
				return !TextUtils.isEmpty((CharSequence) fi.value);
			}
		}
		return true;
	}
}
