package com.g.seed.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.g.seed.web.exception.StatusCodeException;
import com.g.seed.web.resultprocessor.StreamHttpResultProcessor;
import com.g.seed.web.resultprocessor.StringHttpResultProcessor;

public class HttpHelper {
	private String location;
	private POTool potool;
	private static int timeout = 30 * 1000;
	private DefaultHttpClient client;
	
	public HttpHelper() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
		HttpConnectionParams.setSoTimeout(httpParams, timeout);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
		HttpClientParams.setRedirecting(httpParams, true);
		this.client = new DefaultHttpClient(httpParams);
	}
	
	public HttpHelper(String location) {
		this();
		this.potool = new POTool();
		this.location = location;
	}
	
	public HttpHelper(String location, IEncryptor encryptor) {
		this(location);
		setEncryptor(encryptor);
	}
	
	public String get(String action, Map<String, Object> params)
			throws ParseException, ClientProtocolException, StatusCodeException, IOException {
		String param = this.potool.changeStr(params);
		return (new StringHttpResultProcessor(get(action, param)).exe());
	}
	
	public String get(String action, Object po) throws ParseException, ClientProtocolException, StatusCodeException, IOException {
		return (new StringHttpResultProcessor(geta(action, po)).exe());
	}
	
	public HttpResponse geta(String action, Object po) throws ParseException, ClientProtocolException, StatusCodeException,
			IOException {
		return get(action, this.potool.changeStr(po));
	}
	
	public HttpResponse get(String action, String params) throws ClientProtocolException, IOException {
		return client.execute(new HttpGet(this.location + action + params));
	}
	
	public String postString(String action, Object... po) throws ClientProtocolException, IOException, StatusCodeException {
		return new StringHttpResultProcessor(post(action, po)).exe();
	}
	
	public InputStream postStream(String action, Object... po) throws ClientProtocolException, IOException, StatusCodeException {
		return new StreamHttpResultProcessor(post(action, po)).exe();
	}
	
	public HttpResponse post(String action, Object... poarray) throws ClientProtocolException, IOException {
		return post(action, poarray != null ? this.potool.change(poarray) : null);
	}

	public HttpResponse post(String action, int timeout, Object... poarray) throws ClientProtocolException, IOException {
		HttpPost httpRequest = new HttpPost(this.location + action);
		if (poarray != null)
			httpRequest.setEntity(new UrlEncodedFormEntity(this.potool.change(poarray), "UTF-8"));
		HttpParams httpParams = httpRequest.getParams().copy();
		HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
		HttpConnectionParams.setSoTimeout(httpParams, timeout);
		httpRequest.setParams(httpParams);
		return client.execute(httpRequest);
	}
	
	public HttpResponse post(String action, List<NameValuePair> params) throws ClientProtocolException, IOException {
		HttpPost httpRequest = new HttpPost(this.location + action);
		if (params != null)
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		return client.execute(httpRequest);
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setEncryptor(IEncryptor encryptor) {
		this.potool.setEncryptor(encryptor);
	}
	
	public static void setTimeout(int timeout) {
		HttpHelper.timeout = timeout;
	}
}