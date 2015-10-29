/**
 * 
 */
package com.g.seed.textresolver;

import java.util.Map.Entry;

import com.g.seed.autowired.Params;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * @ClassName: GInterpreter
 * @author zhigeng.ni
 * @date 2015年8月10日 下午4:01:06
 * @Description: TODO (描述作用)
 * 				
 */
public class GInterpreter extends Interpreter {
	
	private static final long serialVersionUID = 1L;
	private Params params;
	
	private GInterpreter() {}
	
	private static GInterpreter instance = new GInterpreter();
	
	public static GInterpreter getInstance(Params params) {
		instance.setParams(params);
		return instance;
	}
	
	public void setParams(Params params) {
		if (this.params != null) {
			for (String key : this.params.keySet()) {
				unset(key);
			}
		}
		for (Entry<String, Object> entry : params.entrySet()) {
			set(entry.getKey(), entry.getValue());
		}
		this.params = params;
	}
	
	public void unset(String key) {
		try {
			super.unset(key);
		} catch (EvalError e) {
			e.printStackTrace();
		}
	}
	
	public void set(String name, Object value) {
		try {
			super.set(name, value);
		} catch (EvalError e) {
			e.printStackTrace();
		}
	}
	
	public Params getParams(){
		return this.params;
	}
	
}
