package com.g.seed.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt
{
	/** 
	* @Title: value 
	* @Description: TODO (要使用的加密类型) 
	* @return
	* @return EncryptType
	* @throws 
	*/
	public abstract EncryptType value() default EncryptType.RSA;
	
	/** 
	* @Title: ekeyType 
	* @Description: TODO (要使用的秘钥类型) 
	* @return
	* @return EKeyType
	* @throws 
	*/
	public abstract EKeyType ekeyType() default EKeyType.PublicKey;
	
}
