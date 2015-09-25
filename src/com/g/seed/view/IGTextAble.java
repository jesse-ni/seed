/**
 * 
 */
package com.g.seed.view;

import com.g.seed.textresolver.EL;

/** 
* @ClassName: IGTextAble 
* @author zhigeng.ni 
* @date 2015年8月17日 下午5:07:44 
* @Description: TODO (描述作用) 
*  
*/
public interface IGTextAble {
	void setExpression(String string);
	String getExpression();
	void update(EL el);
	void update(Object bean); 
	void setText(CharSequence charSequence);
	<T> T getDA(String name);
	void setDA(String name, Object value);
}
