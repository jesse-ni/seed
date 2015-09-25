/**
 * 
 */
package com.g.seed.autowired;

import java.util.ArrayList;
import java.util.List;

import com.g.seed.view.IFormElement;
import com.g.seed.view.IGTextAble;

/**
 * @ClassName: ViewHolder
 * @author zhigeng.ni
 * @date 2015年9月2日 下午1:25:58
 * @Description: TODO (描述作用)
 * 				
 */
public class ExtendedAttribute {
	private List<IGTextAble> textAbles = new ArrayList<IGTextAble>();
	private List<IFormElement> formElements = new ArrayList<IFormElement>();
	
	public void add(IGTextAble textAble) {
		textAbles.add(textAble);
	}
	
	public void add(IFormElement formElement) {
		formElements.add(formElement);
	}

	public List<IGTextAble> getTextAbles() {
		return textAbles;
	}

	public List<IFormElement> getFormElements() {
		return formElements;
	}
	
}
