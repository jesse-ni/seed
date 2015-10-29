/**
 * 
 */
package com.g.seed.autowired;

import android.text.Spanned;

/**
 * @ClassName: TextAbleR
 * @author zhigeng.ni
 * @date 2015年9月7日 下午1:34:43
 * @Description: TODO (描述作用)
 * 				
 */
public class TextAbleR {
	Spanned content;
	String expression;
	
	public TextAbleR(Spanned content, String expression) {
		super();
		this.content = content;
		this.expression = expression;
	}
	
	/**
	 * @return the content
	 */
	public Spanned getContent() {
		return content;
	}
	
	/**
	 * @param content the content to set
	 */
	public void setContent(Spanned content) {
		this.content = content;
	}
	
	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}
	
	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
