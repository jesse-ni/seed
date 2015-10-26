/**
 * 
 */
package com.g.seed.web.service;

/**
 * @ClassName: IWebService
 * @author zhigeng.ni
 * @date 2015年8月19日 上午11:11:25
 * @Description: TODO (描述作用)
 * 				
 */
public interface IForm {
	public String getAction();
	public Enctype getEnctype();
	public HttpMethod getMethod();
}
