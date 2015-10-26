/**
 * 
 */
package com.g.seed.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.entity.mime.MultipartEntity;
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
public class ParamPository2 implements IParamPository {
	public ParamPository2(MultipartEntity multipartEntity) {
		this.multipartEntity = multipartEntity;
	}
	
	private MultipartEntity multipartEntity;
	
	@Override
	public void add(String name, Object value) {
		ContentBody contentBody = null;
		if (value instanceof File) {
			contentBody = new FileBody((File) value);
		} else {
			try {
				contentBody = new StringBody(String.valueOf(value), Charset.forName("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		multipartEntity.addPart(name, contentBody);
	}
	
}
