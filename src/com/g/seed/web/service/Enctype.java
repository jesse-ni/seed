/**
 * 
 */
package com.g.seed.web.service;

/**
 * @ClassName: Enctype
 * @author zhigeng.ni
 * @date 2015年10月20日 下午1:25:41
 * @Description: TODO (描述作用)
 * 				
 */
public enum Enctype {
	urlencoded("application/x-www-form-urlencoded"), multipart("multipart/form-data"), text("text/plain");
	
	private String value;
	
	private Enctype(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
	
}
