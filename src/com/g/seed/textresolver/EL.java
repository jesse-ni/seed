package com.g.seed.textresolver;

import com.g.seed.autowired.Params;
import com.g.seed.util.MRTXT;

import android.util.Log;

public class EL
{
	private final String begin = "${";
	private final String end = "}";
	private final GInterpreter interpreter;
	
	public EL(Params params){
		this.interpreter = GInterpreter.getInstance(params);
		setParams(params);
	}
	
	public EL(Object obj) {
		this(new Params(obj));
	}
	
	private String el(String str)
	{
		int beginIndex = str.indexOf(begin);
		if (beginIndex == -1)
			return str;
		int endIndex = str.indexOf(end);
		String el = str.substring(beginIndex, endIndex + 1);
		return el(str.replace(el, String.valueOf(analyze(el))));
	}
	
	public String exe(String str) {
		return el(str);
	}
	
	public Object analyze(String tag) {
		return tag == null ? null : analyze2(getTagContent(tag));
	}
	
	public Object analyze2(String tagContent) {
		String[] c;
		try {
			c = tagContent.split("~:");
			if (c.length > 2)
				throw new RuntimeException("EL Exception");
			Object value = this.interpreter.eval(c[0]);
			if (c.length == 1)
				return ((value == null) ? "null" : value);
			return analyze3(value, c[1]);
		} catch (Exception e) {
			Log.e(super.getClass().getName(), e.getMessage());
		}
		return null;
	}
	
	public Object analyze3(Object value, String op) {
		if (value == null)
			return String.valueOf(value);
		if (op.matches("[0-9]+"))
			return MRTXT.holdC(value, Integer.parseInt(op));
		if (op.equals("P"))
			return MRTXT.phoneFormat(value.toString());
		
		return value;
	}
	
	private String getTagContent(String tag) {
		return tag.substring(begin.length(), tag.length() - 1);
	}
	
	public void setParams(Params params){
		this.interpreter.setParams(params);
	}
	
	public GInterpreter getGInterpreter() {
		return this.interpreter;
	}
	
}
