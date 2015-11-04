/**
 * 
 */
package com.g.seed.web;

import java.io.File;

import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.g.seed.web.service.IParamPository;

/**
 * @ClassName: ParamPository1
 * @author zhigeng.ni
 * @date 2015年10月20日 下午4:10:53
 * @Description: TODO (描述作用)
 * 				
 */
public class ParamPositoryForMultipartEntity implements IParamPository {
	public ParamPositoryForMultipartEntity(MultipartEntityBuilder multipartEntityBuilder) {
		this.multipartEntityBuilder = multipartEntityBuilder;
	}
	
	private MultipartEntityBuilder multipartEntityBuilder;
	
	@Override
	public void add(String name, Object value) {
		ContentBody contentBody = null;
		if (value instanceof File) {
			contentBody = new FileBody((File) value);
		} else {
			contentBody = new StringBody(String.valueOf(value), ContentType.create("text/plain", Consts.UTF_8));
		}
		multipartEntityBuilder.addPart(name, contentBody);
	}
	
}
