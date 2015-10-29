/**
 * 
 */
package com.g.seed.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @ClassName: Name 
 * @author zhigeng.ni 
 * @date 2015年7月18日 下午12:42:35 
 * @Description: TODO (描述作用) 
 *  
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {
	
	public abstract String value();
	
}
