package com.g.seed.web.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.g.seed.util.LOGTAG;
import com.g.seed.web.HttpHelper;
import com.g.seed.web.IEncryptor;
import com.g.seed.web.POTool;
import com.g.seed.web.task.MyAsyncTask;
import com.g.seed.web.task.MyAsyncTask.AsyncResultListener;

import android.util.Log;

public class Service {
	public Service(String location, IEncryptor myEncryptor) {
		poTool = new POTool(myEncryptor);
		httpHelper = new HttpHelper(location, myEncryptor);
	}
	
	protected POTool poTool;
	protected HttpHelper httpHelper;
	private static Service instance = null;
	
	public synchronized static void init(String location, IEncryptor myEncryptor) {
		instance = new Service(location, myEncryptor);
	}
	
	public synchronized static Service getInstance() {
		if (instance == null) { throw new NullPointerException("instance == null"); }
		return instance;
	}
	
	public HttpResponse syncPost(final String action, final Object po)
			throws ClientProtocolException, IOException {
		log(po);
		return httpHelper.post(action, po);
	}
	
	public void asyncPost(final String action, final Object po, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + action, l) {
			
			@Override
			protected HttpResponse doRequest() throws ClientProtocolException, IOException {
				return syncPost(action, po);
			}
			
		}.execute();
	}
	
	public void asyncPostMultipart(final String action, final Object po, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + action, l) {
			
			@Override
			protected HttpResponse doRequest() throws ClientProtocolException, IOException {
				log(po);
				return httpHelper.postMultipart(action, po);
			}
			
		}.execute();
	}
	
	public void asyncPost(final String action, final Object po, final int timeout, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + action, l) {
			
			@Override
			protected HttpResponse doRequest() throws ClientProtocolException, IOException {
				log(po);
				return httpHelper.post(action, timeout, new Object[] { po });
			}
			
		}.execute();
	}
	
	private void log(final Object po) {
		Log.i(LOGTAG.RequestInfo, "param:" + (po != null ? poTool.toString(po) : "no parameter"));
		Log.i(LOGTAG.RequestInfo, "parae:" + (po != null ? poTool.toStringE(po) : "no parameter"));
	}
}
