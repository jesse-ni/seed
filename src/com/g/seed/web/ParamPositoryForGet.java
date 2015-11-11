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
public class ParamPositoryForGet extends ParamPositoryBase {
	public ParamPositoryForGet(StringBuffer params, HttpMessage httpMessage) {
		super(httpMessage);
		this.params = params;
	}
	
	private StringBuffer params;

	@Override
	public void add(String name, Object value) {
		params.append("&" + name + "=" + value);
	}

}
