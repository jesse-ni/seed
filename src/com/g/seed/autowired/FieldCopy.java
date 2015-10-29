package com.g.seed.autowired;

import java.lang.reflect.Field;

public class FieldCopy {
	public static void exe(Object a, Object b) {
		try {
			Field[] arrayOfField;
			int j = (arrayOfField = a.getClass().getDeclaredFields()).length;
			for (int i = 0; i < j; ++i) {
				Field fielda = arrayOfField[i];
				fielda.setAccessible(true);
				Field fieldb = b.getClass().getDeclaredField(fielda.getName());
				fieldb.setAccessible(true);
				fieldb.set(b, fielda.get(a));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
