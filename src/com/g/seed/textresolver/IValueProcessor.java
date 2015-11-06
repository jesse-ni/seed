/**
 * 
 */
package com.g.seed.textresolver;

/**
 * @ClassName: IValueProcessor
 * @author zhigeng.ni
 * @date 2015年11月6日 下午2:54:59
 * @Description: TODO (描述作用)
 * 				
 */
public interface IValueProcessor {
	public Object exe(Object value, String[] params);
	public boolean isFinal();
}
