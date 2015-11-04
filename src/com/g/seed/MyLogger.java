/**
 * 
 */
package com.g.seed;

import android.util.Log;

/**
 * @ClassName: MyLogger
 * @author zhigeng.ni
 * @date 2015年11月2日 下午1:50:19
 * @Description: TODO (描述作用)
 * 				
 */
public class MyLogger {
	public static void d(String tag, String msg) {
		if (SeedContext.showDebugInfo) {
			Log.d(tag, msg);
		}
	}
	
	public static void i(String tag, String msg) {
		if (SeedContext.showDebugInfo) {
			Log.i(tag, msg);
		}
	}
	
	public static void w(String tag, String msg) {
		if (SeedContext.showDebugInfo) {
			Log.w(tag, msg);
		}
	}
	
	public static void v(String tag, String msg) {
		if (SeedContext.showDebugInfo) {
			Log.v(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (SeedContext.showDebugInfo) {
			Log.e(tag, msg);
		}
	}
}
