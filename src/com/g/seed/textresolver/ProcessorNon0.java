/**
 * 
 */
package com.g.seed.textresolver;

/**
 * @ClassName: Processor1
 * @author zhigeng.ni
 * @date 2015年11月6日 下午3:15:48
 * @Description: TODO (如果值是数字0则返回空串并且中断后续操作)
 * 				
 */
public class ProcessorNon0 implements IValueProcessor {
	boolean isFinal = false;
	
	@Override
	public Object exe(Object value, String[] params) {
		if (isFinal = value instanceof Number && value.toString().equals("0")) { return ""; }
		return value;
	}
	
	@Override
	public boolean isFinal() {
		return isFinal;
	}
	
}
