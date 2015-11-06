/**
 * 
 */
package com.g.seed.textresolver;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: Processor1
 * @author zhigeng.ni
 * @date 2015年11月6日 下午3:15:48
 * @Description: TODO (将时间戳转化为指定日期格式)
 * 				
 */
public class ProcessorDate implements IValueProcessor {
	
	@Override
	public Object exe(Object value, String[] params) {
		String v = value.toString();
		if (v.length() < 13) {
			for (int i = v.length(); i < 13; i++) {
				v += "0";
			}
		}
		Date date = new Date(Long.parseLong(v));
		String format = params[0];
		return new SimpleDateFormat(format).format(date);
	}
	
	@Override
	public boolean isFinal() {
		return false;
	}
	
}
