/**
 * 
 */
package com.g.seed;

import org.apache.http.client.config.RequestConfig;

import com.g.seed.web.IEncryptor;

/** 
* @ClassName: SeedContext 
* @author zhigeng.ni 
* @date 2015年11月2日 下午1:33:12 
* @Description: TODO (描述作用) 
*  
*/
public class SeedContext {
	public static boolean showDebugInfo = false;
	public static RequestConfig defaultRequestConfig = RequestConfig.custom().build();
	public static IEncryptor encryptor;
}
