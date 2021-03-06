/**
 * 
 */
package com.g.seed.web;

import com.g.seed.web.service.IParamPository;

/**
 * @ClassName: ParamPository1
 * @author zhigeng.ni
 * @date 2015年10月20日 下午4:10:53
 * @Description: TODO (描述作用)
 * 				
 */
public class ParamPositoryForDebugInfo implements IParamPository {
	public ParamPositoryForDebugInfo(StringBuffer params) {
		this.params = params;
	}
	
	private StringBuffer params;
	
	@Override
	public void add(String name, Object value) {
		params.append("    " + name + " = " + value + "\n");
	}

	@Override
	public void addHeader(String name, Object value) {
		params.append("    @Header " + name + " = " + value + "\n");
	}
	
}
