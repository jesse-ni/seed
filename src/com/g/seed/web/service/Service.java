package com.g.seed.web.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.g.seed.util.LOGTAG;
import com.g.seed.web.HttpHelper;
import com.g.seed.web.IEncryptor;
import com.g.seed.web.POTool;
import com.g.seed.web.task.MyAsyncTask;
import com.g.seed.web.task.MyAsyncTask.AsyncResultListener;

public class Service implements IWebService {
	protected IEncryptor myEncryptor; // = new MyEncryptor();
	protected POTool poTool = new POTool(myEncryptor);
	protected HttpHelper httpHelper = new HttpHelper("URLs.WebUrl", myEncryptor);
	
	@Override
	public HttpResponse syncPost(final String action, final Object po)
			throws ClientProtocolException, IOException {
		String missionName = httpHelper.getLocation() + action;
		Log.i(LOGTAG.RequestInfo, "request for mission " + missionName + "\nparameter:" + poTool.toString(po));
		Log.i(LOGTAG.RequestInfo, "\nencrypted parameter:" + poTool.toStringE(po));
		return httpHelper.post(action, po);
	}
	
	@Override
	public void asyncPost(final String action, final Object po, final AsyncResultListener l) {
		String missionName = httpHelper.getLocation() + action;
		new MyAsyncTask(missionName, l) {
			
			@Override
			protected HttpResponse doRequest() throws ClientProtocolException, IOException {
				return syncPost(action, po);
			}
			
		}.execute();
	}
}
