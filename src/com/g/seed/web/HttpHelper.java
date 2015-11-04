package com.g.seed.web;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.client.methods.HttpRequestBaseHC4;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.g.seed.SeedContext;
import com.g.seed.web.service.Enctype;
import com.g.seed.web.service.HttpMethod;
import com.g.seed.web.service.IForm;

public class HttpHelper {
	private String location;
	private POTool potool = new POTool();
	private CloseableHttpClient client;
	
	public HttpHelper() {	
		this.client = HttpClients.createDefault();
	}
	
	public HttpHelper(String location) {
		this();
		this.location = location;
	}
	
	public HttpResponse submit(IForm form) throws Exception {
		return submit(form, SeedContext.defaultRequestConfig);
	}

	public HttpResponse submit(IForm form, RequestConfig config) throws Exception {
		final HttpRequestBaseHC4 request = buildRequest(form);
		request.setConfig(config);
		return client.execute(request);
	}

	public HttpResponse submit(Object headers, IForm form) throws Exception {
		return submit(headers, form, SeedContext.defaultRequestConfig);
	}

	public HttpResponse submit(Object headers, IForm form, RequestConfig config) throws Exception {
		final HttpRequestBaseHC4 request = buildRequest(form);
		potool.putHeaders(request, headers);
		request.setConfig(config);
		return client.execute(request);
	}

	public HttpRequestBaseHC4 buildRequest(IForm form) throws Exception {
		if (form.getMethod() == HttpMethod.POST) {
			final HttpPostHC4 httpPost = new HttpPostHC4(this.location + form.getAction());
			if (form.getEnctype() == Enctype.multipart) {
				MultipartEntityBuilder reqEntityBuilder = MultipartEntityBuilder.create();
				this.potool.changeToMultipart(reqEntityBuilder, form);
				httpPost.setEntity(reqEntityBuilder.build());
			} else if (form.getEnctype() == Enctype.urlencoded) {
				httpPost.setEntity(new UrlEncodedFormEntity(this.potool.change(form), "UTF-8"));
			} else {
				throw new RuntimeException("unsupport enctype!");
			}
			return httpPost;
		} else {
			return new HttpGetHC4(this.location + form.getAction() + this.potool.changeStr(form));
		}
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
}
