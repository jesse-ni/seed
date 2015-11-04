/**
 * 
 */
package com.g.seed.util;

import com.g.seed.web.result.Result.Judge;

/**
 * @ClassName: MyJudge
 * @author zhigeng.ni
 * @date 2015年10月30日 上午11:57:53
 * @Description: TODO (描述作用)
 * 				
 */
public class MyJudge implements Judge {
	private final String[] allowedCode;
	
	public MyJudge(String... allowedCode) {
		super();
		this.allowedCode = allowedCode;
	}
	
	@Override
	public boolean isNormal(String code) {
		for (String unit : allowedCode) {
			if (code.equals(unit)) {
				return true;
			}
		}
		return false;
	}
	
}
