/**
 * 
 */
package com.g.seed.web.form;

import com.g.seed.web.Header;
import com.g.seed.web.Ignore;
import com.g.seed.web.service.Enctype;
import com.g.seed.web.service.HttpMethod;

/**
 * @ClassName: Download
 * @author zhigeng.ni
 * @date 2015年11月9日 下午2:31:57
 * @Description: TODO (描述作用)
 * 				
 */
public class DownloadleForm implements IForm {
	public DownloadleForm(String action, String range) {
		super();
		this.range = range;
		this.action = action;
	}
	
	@Ignore
	String action;

	@Header
	String range;
	
	
	@Override
	public String getAction() {
		return action;
	}
	
	@Override
	public Enctype getEnctype() {
		return Enctype.urlencoded;
	}
	
	@Override
	public HttpMethod getMethod() {
		return HttpMethod.GET;
	}
	
}
