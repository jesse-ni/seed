/**
 * 
 */
package com.g.seed.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @ClassName: Util
 * @author zhigeng.ni
 * @date 2015年10月15日 下午1:48:04
 * @Description: TODO (描述作用)
 * 				
 */
public class Util {
	public static boolean isPrimitive(Class<?> clz) {
		if (clz == null) { throw new NullPointerException(); }
		try {
			final boolean b = clz.equals(String.class) || clz.equals(Date.class) || clz.equals(BigInteger.class) || clz.equals(BigDecimal.class);
			return b ? b : ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}
}
