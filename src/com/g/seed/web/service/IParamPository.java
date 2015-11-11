/**
 * 
 */
package com.g.seed.web.service;

/** 
* @ClassName: IParamPository 
* @author zhigeng.ni 
* @date 2015年10月20日 下午3:30:59 
* @Description: TODO (描述作用) 
*  
*/
public interface IParamPository {
	public void add(String name, Object value);

	void addHeader(String name, Object value);
}
