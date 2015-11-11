/**
 * 
 */
package com.g.seed.web;

import java.util.List;

import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/** 
* @ClassName: ParamPository1 
* @author zhigeng.ni 
* @date 2015年10月20日 下午4:10:53 
* @Description: TODO (描述作用) 
*  
*/
public class ParamPositoryForPost extends ParamPositoryBase {
	public ParamPositoryForPost(List<NameValuePair> params, HttpMessage httpMessage) {
		super(httpMessage);
		this.params = params;
	}
	
	private List<NameValuePair> params;

	@Override
	public void add(String name, Object value) {
		params.add(new BasicNameValuePair(name, String.valueOf(value)));
	}

}
