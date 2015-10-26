package com.g.seed.textresolver;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.g.seed.autowired.Params;
import com.g.seed.util.MRTXT;
import android.annotation.SuppressLint;
import android.util.Log;
import bsh.EvalError;

public class EL {
	private final String begin = "${";
	private final String end = "}";
	private final GInterpreter interpreter;
	
	public EL(Params params) {
		this.interpreter = GInterpreter.getInstance(params);
		setParams(params);
	}
	
	public EL(Object obj) {
		this(new Params(obj));
	}
	
	private String el(String str) {
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
		String[] c = tagContent.split("~:");
		if (c.length > 2)
			throw new RuntimeException("EL Exception");
		Object value = eval(c);
		if (c.length == 1)
			return String.valueOf(value);
		return analyze3(value, c[1]);
	}
	
	private Object eval(String[] c) {
		try {
			return this.interpreter.eval(c[0]);
		} catch (EvalError e) {
			Log.e("EL", "eval fail", e);
			return null;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public Object analyze3(Object value, String op) {
		if (value == null)
			return "";
		if (op.endsWith("#")) {
			op = op.substring(0, op.length() - 1);
			if (value instanceof Number && value.toString().equals("0")) { return ""; }
		}
		if (op.matches("[0-9]+"))
			return MRTXT.holdC(value, Integer.parseInt(op));
		if (op.equals("P"))
			return MRTXT.phoneFormat(value.toString());
		if (op.equals("nullable"))
			return value == null ? "" : value;
		if (op.startsWith("Date:")) {
			String v = value.toString();
			if (v.length() < 13) {
				for (int i = v.length(); i < 13; i++) {
					v += "0";
				}
			}
			Date date = new Date(Long.parseLong(v));
			String format = op.substring("Date:".length());
			return new SimpleDateFormat(format).format(date);
		}
		return value;
	}
	
	private String getTagContent(String tag) {
		return tag.substring(begin.length(), tag.length() - 1);
	}
	
	public void setParams(Params params) {
		this.interpreter.setParams(params);
	}
	
	public GInterpreter getGInterpreter() {
		return this.interpreter;
	}
	
}
