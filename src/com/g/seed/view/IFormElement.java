/**
 * 
 */
package com.g.seed.view;

import org.apache.http.NameValuePair;

/** 
* @ClassName: IFormElement 
* @author zhigeng.ni 
* @date 2015年8月17日 下午5:09:22 
* @Description: TODO (描述作用) 
*  
*/
public interface IFormElement extends ICheckAble {
	String value();

	boolean isNecessary();

	NameValuePair build();

	String getName();
}
