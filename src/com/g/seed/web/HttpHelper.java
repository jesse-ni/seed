package com.g.seed.web;

import java.net.URI;

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
import com.g.seed.web.form.IForm;
import com.g.seed.web.service.Enctype;
import com.g.seed.web.service.HttpMethod;

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
		return buildRequest(form.getAction(), form, form.getMethod(), form.getEnctype());
	}
	
	public HttpResponse get(String action, Object po) throws Exception {
		return get(action, po, SeedContext.defaultRequestConfig);
	}
	
	public HttpResponse get(String action, Object po, RequestConfig config) throws Exception {
		final HttpRequestBaseHC4 request = buildRequest(action, po, HttpMethod.GET, Enctype.urlencoded, config);
		return client.execute(request);
	}
	
	public HttpResponse post(String action, Object po) throws Exception {
		return post(action, po, SeedContext.defaultRequestConfig);
	}
	
	public HttpResponse post(String action, Object po, RequestConfig config) throws Exception {
		final HttpRequestBaseHC4 request = buildRequest(action, po, HttpMethod.POST, Enctype.urlencoded, config);
		return client.execute(request);
	}
	
	public HttpRequestBaseHC4 buildRequest(String action, Object po, HttpMethod httpMethod, Enctype enctype, RequestConfig config) throws Exception {
		final HttpRequestBaseHC4 request = buildRequest(action, po, httpMethod, enctype);
		request.setConfig(config);
		return request;
	}
	
	public HttpRequestBaseHC4 buildRequest(String action, Object po, HttpMethod httpMethod, Enctype enctype) throws Exception {
		if (httpMethod == HttpMethod.POST) {
			final HttpPostHC4 httpPost = new HttpPostHC4(this.location + action);
			if (enctype == Enctype.multipart) {
				MultipartEntityBuilder reqEntityBuilder = MultipartEntityBuilder.create();
				this.potool.changeToMultipart(reqEntityBuilder, httpPost, po);
				httpPost.setEntity(reqEntityBuilder.build());
			} else if (enctype == Enctype.urlencoded) {
				httpPost.setEntity(new UrlEncodedFormEntity(this.potool.change(httpPost, po), "UTF-8"));
			} else {
				throw new RuntimeException("unsupport enctype!");
			}
			return httpPost;
		} else {
			HttpGetHC4 httpGet = new HttpGetHC4();
			httpGet.setURI(URI.create(this.location + action + this.potool.changeStr(httpGet, po)));
			return httpGet;
		}
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
}
