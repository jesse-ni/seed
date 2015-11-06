/**
 * 
 */
package com.g.seed.textresolver;

import com.g.seed.util.MRTXT;

/**
 * @ClassName: Processor1
 * @author zhigeng.ni
 * @date 2015年11月6日 下午3:15:48
 * @Description: TODO (保留指定位数的小数位)
 * 				
 */
public class ProcessorDecimal implements IValueProcessor {
	
	@Override
	public Object exe(Object value, String[] params) {
		return MRTXT.holdC(value, Integer.parseInt(params[0]));
	}
	
	@Override
	public boolean isFinal() {
		return false;
	}
	
}
