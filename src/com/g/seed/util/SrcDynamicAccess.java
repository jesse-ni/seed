package com.g.seed.util;

import android.content.Context;

public class SrcDynamicAccess {
	private static String _packgeName;
	private static Class<?> _srcLayout;
	private static Class<?> _srcID;
	private static Class<?> _srcString;
	private static Class<?> _srcInteger;
	
	public static void ready(Context context) {
		try {
			_packgeName = context.getPackageName();
			_srcLayout = Class.forName(_packgeName + ".R$layout");
			_srcID = Class.forName(_packgeName + ".R$id");
			_srcString = Class.forName(_packgeName + ".R$string");
			_srcInteger = Class.forName(_packgeName + ".R$integer");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	public static Integer getLayout(String name) {
		try {
			return _srcLayout.getField(name).getInt(null);
		} catch (Exception e) {
			System.out.println("execption _srcLayout:" + name);
			return null;
		}
	}
	
	public static Integer getID(String name) {
		try {
			return _srcID.getField(name).getInt(null);
		} catch (Exception e) {
			System.out.println("execption _srcID:" + name);
			return null;
		}
	}
	
	public static Integer getString(String name) {
		try {
			return _srcString.getField(name).getInt(null);
		} catch (Exception e) {
			System.out.println("execption _srcString:" + name);
			return null;
		}
	}
	
	public static Integer getInteger(String name) {
		try {
			return _srcInteger.getField(name).getInt(null);
		} catch (Exception e) {
			System.out.println("execption _srcInteger:" + name);
			return null;
		}
	}
}
