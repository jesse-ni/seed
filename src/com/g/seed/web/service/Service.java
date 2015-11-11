package com.g.seed.web.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;

import com.g.seed.MyLogger;
import com.g.seed.util.LOGTAG;
import com.g.seed.web.HttpHelper;
import com.g.seed.web.POTool;
import com.g.seed.web.form.IForm;
import com.g.seed.web.task.MyAsyncTask;
import com.g.seed.web.task.MyAsyncTask.AsyncResultListener;

public class Service {
	public Service(String location) {
		httpHelper = new HttpHelper(location);
	}
	
	protected POTool poTool = new POTool();
	protected HttpHelper httpHelper;
	private static Service instance = null;
	
	public synchronized static void init(String location) {
		instance = new Service(location);
	}
	
	public synchronized static Service getInstance() {
		if (instance == null) { throw new NullPointerException("instance == null"); }
		return instance;
	}
	
	public HttpResponse submit(final IForm form) throws Exception {
		log(form);
		return httpHelper.submit(form);
	}
	
	public HttpResponse submit(IForm form, RequestConfig config) throws Exception {
		log(form);
		return httpHelper.submit(form, config);
	}
	
	public HttpResponse submit(Object headers, IForm form) throws Exception {
		log(form);
		return httpHelper.submit(headers, form);
	}
	
	public HttpResponse submit(Object headers, IForm form, RequestConfig config) throws Exception {
		log(form);
		return httpHelper.submit(headers, form, config);
	}
	
	
	public HttpResponse get(String action, Object po) throws Exception {
		log(po);
		return httpHelper.get(action, po);
	}

	public HttpResponse get(String action, Object po, RequestConfig config) throws Exception {
		log(po);
		return httpHelper.get(action, po, config);
	}

	public HttpResponse post(String action, Object po) throws Exception {
		log(po);
		return httpHelper.post(action, po);
	}

	public HttpResponse post(String action, Object po, RequestConfig config) throws Exception {
		log(po);
		return httpHelper.post(action, po, config);
	}
	
	public void asyncGet(final String action, final Object po, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + action, l) {
			
			@Override
			protected HttpResponse doRequest() throws Exception {
				return Service.this.get(action, po);
			}
			
		}.execute();
	}
	
	public void asyncGet(final String action, final Object po, final RequestConfig config, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + action, l) {
			
			@Override
			protected HttpResponse doRequest() throws Exception {
				return Service.this.get(action, po, config);
			}
			
		}.execute();
	}
	
	public void asyncPost(final String action, final Object po, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + action, l) {
			
			@Override
			protected HttpResponse doRequest() throws Exception {
				return Service.this.post(action, po);
			}
			
		}.execute();
	}
	
	public void asyncPost(final String action, final Object po, final RequestConfig config, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + action, l) {
			
			@Override
			protected HttpResponse doRequest() throws Exception {
				return Service.this.post(action, po, config);
			}
			
		}.execute();
	}

	public void asyncSubmit(final IForm form, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + form.getAction(), l) {
			
			@Override
			protected HttpResponse doRequest() throws Exception {
				return submit(form);
			}
			
		}.execute();
	}
	
	public void asyncSubmit(final Object headers, final IForm form, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + form.getAction(), l) {
			
			@Override
			protected HttpResponse doRequest() throws Exception {
				return submit(headers, form);
			}
			
		}.execute();
	}
	
	public void asyncSubmit(final IForm form, final RequestConfig config, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + form.getAction(), l) {
			
			@Override
			protected HttpResponse doRequest() throws Exception {
				return submit(form, config);
			}
			
		}.execute();
	}
	
	public void asyncSubmit(final Object headers, final IForm form, final RequestConfig config, final AsyncResultListener l) {
		new MyAsyncTask(httpHelper.getLocation() + form.getAction(), l) {
			
			@Override
			protected HttpResponse doRequest() throws Exception {
				return submit(headers, form, config);
			}
			
		}.execute();
	}
	
	private void log(final Object po) {
		MyLogger.i(LOGTAG.RequestInfo, "param:" + (po != null ? poTool.toString(po) : "no parameter"));
		MyLogger.i(LOGTAG.RequestInfo, "parae:" + (po != null ? poTool.toStringE(po) : "no parameter"));
	}
}
