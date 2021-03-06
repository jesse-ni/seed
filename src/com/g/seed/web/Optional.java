package com.g.seed.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Optional
{
	/** 
	* @Title: value 
	* @Description: TODO (值为true时允许空字符串添加到参数中) 
	* @return
	* @return boolean
	* @throws 
	*/
	boolean value() default false;
}
