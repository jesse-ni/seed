/**
 * 
 */
package com.g.seed.textresolver;

import com.g.seed.util.MRTXT;

/**
 * @ClassName: Processor1
 * @author zhigeng.ni
 * @date 2015年11月6日 下午3:15:48
 * @Description: TODO (如果值是数字0则返回空串并且中断后续操作)
 * 				
 */
public class ProcessorPhone implements IValueProcessor {
	
	@Override
	public Object exe(Object value, String[] params) {
		return MRTXT.phoneFormat(value.toString());
	}
	
	@Override
	public boolean isFinal() {
		return false;
	}
	
}
