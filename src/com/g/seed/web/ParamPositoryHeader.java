/**
 * 
 */
package com.g.seed.web;

import org.apache.http.HttpMessage;

/**
 * @ClassName: ParamPository1
 * @author zhigeng.ni
 * @date 2015年10月20日 下午4:10:53
 * @Description: TODO (描述作用)
 * 				
 */
public class ParamPositoryHeader extends ParamPositoryBase {
	public ParamPositoryHeader(HttpMessage httpMessage) {
		super(httpMessage);
	}
	
	@Override
	public void add(String name, Object value) {
		super.addHeader(name, value);
	}
	
}
