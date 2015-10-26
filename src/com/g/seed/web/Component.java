/**
 * 
 */
package com.g.seed.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @ClassName: Component 
* @author zhigeng.ni 
* @date 2015年10月20日 下午5:21:52 
* @Description: TODO (描述作用) 
*  
*/
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

}
