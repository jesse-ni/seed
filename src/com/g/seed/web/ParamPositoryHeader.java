/**
 * 
 */
package com.g.seed.web;

import org.apache.http.HttpMessage;

import com.g.seed.web.service.IParamPository;

/**
 * @ClassName: ParamPository1
 * @author zhigeng.ni
 * @date 2015年10月20日 下午4:10:53
 * @Description: TODO (描述作用)
 * 				
 */
public class ParamPositoryHeader implements IParamPository {
	public ParamPositoryHeader(HttpMessage httpMessage) {
		this.httpMessage = httpMessage;
	}
	
	private HttpMessage httpMessage;
	
	@Override
	public void add(String name, Object value) {
		httpMessage.addHeader(name, String.valueOf(value));
	}
	
}
