package com.g.seed.textresolver;

import java.util.HashMap;
import java.util.Map;

import com.g.seed.autowired.Params;

import android.util.Log;
import bsh.EvalError;

public class EL {
	private final String begin = "${";
	private final String end = "}";
	private final GInterpreter interpreter;
	private Map<String, IValueProcessor> processors = new HashMap<String, IValueProcessor>();
	
	public EL(Params params) {
		this.interpreter = GInterpreter.getInstance(params);
		setParams(params);
		processors.put("non0", new ProcessorNon0());
		processors.put("decimal", new ProcessorDecimal());
		processors.put("phone", new ProcessorPhone());
		processors.put("Date", new ProcessorDate());
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
		return el(str.replace(el, String.valueOf(analyzeTag(el))));
	}
	
	public String exe(String str) {
		return el(str);
	}
	
	public Object analyzeTag(String tag) {
		return tag == null ? null : analyzeTagContent(getTagContent(tag));
	}
	
	public Object analyzeTagContent(String tagContent) {
		final int indexOfOP = tagContent.indexOf("#");
		String key = indexOfOP == -1 ? tagContent : tagContent.substring(0, indexOfOP);
		Object value = eval(key);
		if (value == null) return "";
		if (indexOfOP == -1) {
			return value;
		}else {
			String[] op = tagContent.substring(indexOfOP + 1).split("#");
			return analyze(value, op);
		}
	}
	
	public Object analyze(Object value, String[] op) {
		for (String unit : op) {
			final int indexOfOP = unit.indexOf(":");
			String opName = indexOfOP == -1 ? unit : unit.substring(0, indexOfOP);
			String[] params = indexOfOP == -1 ? null : unit.substring(indexOfOP + 1).split(",");
			final IValueProcessor processor = processors.get(opName);
			value = processor.exe(value, params);
			if (processor.isFinal()) {
				break;
			}
		}
		return value;
	}
	
	private Object eval(String key) {
		try {
			return this.interpreter.eval(key);
		} catch (EvalError e) {
			Log.e("EL", "eval fail", e);
			return null;
		}
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
