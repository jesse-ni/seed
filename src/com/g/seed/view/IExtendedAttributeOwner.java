/**
 * 
 */
package com.g.seed.view;

import com.g.seed.autowired.ExtendedAttribute;

/** 
* @ClassName: IViewHolderOwner 
* @author zhigeng.ni 
* @date 2015年9月6日 下午3:41:06 
* @Description: TODO (描述作用) 
*  
*/
public interface IExtendedAttributeOwner {
	void setExtendedAttribute(ExtendedAttribute viewHolder);
	ExtendedAttribute getExtendedAttribute();
}
