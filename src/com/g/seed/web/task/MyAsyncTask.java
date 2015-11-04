package com.g.seed.web.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

import com.g.seed.MyLogger;
import com.g.seed.util.LOGTAG;
import com.g.seed.web.exception.StatusCodeException;
import com.g.seed.web.result.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.os.AsyncTask;

public abstract class MyAsyncTask extends AsyncTask<String, Integer, Result> {
	private String missionName = String.valueOf(hashCode());
	
	public MyAsyncTask() {}
	
	public MyAsyncTask(String missionName, AsyncResultListener l) {
		this.l = l;
		this.missionName = missionName;
	}
	
	protected AsyncResultListener l;
	
	@Override
	protected void onPreExecute() {
		l.before(this);
	}
	
	public void cancel() {
		l.after(null);
		cancel(true);
		MyLogger.i(LOGTAG.RequestInfo, "cancel--" + missionName);
	}
	
	@Override
	protected void onPostExecute(Result result) {
		if (result.hasException()) {
			l.exception(result.getException());
		} else {
			if (result.isNormal()) {
				l.normalResult(result);
			} else {
				l.abnormalResult(result);
			}
		}
		l.after(result);
	}
	
	@Override
	protected final Result doInBackground(String... params) {
		try {
			MyLogger.i(LOGTAG.RequestInfo, "request--" + missionName);
			Result result = l.onResponse(doRequest());
			MyLogger.i(LOGTAG.RequestInfo, "response--" + missionName);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(result.printProtype());
			String prettyJsonStr = gson.toJson(je);
			MyLogger.i(LOGTAG.RequestInfo, prettyJsonStr);
			return result;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
			MyLogger.e(LOGTAG.RequestInfo, "exception--" + missionName + "\n" + sw.toString());
			return new Result(e);
		}
	}
	
	protected abstract HttpResponse doRequest() throws Exception;
	
	public void setL(AsyncResultListener l) {
		this.l = l;
	}
	
	public String getMissionName() {
		return missionName;
	}
	
	public interface AsyncResultListener {
		
		public void before(MyAsyncTask task);
		
		/** do in background */
		public Result onResponse(HttpResponse response) throws ParseException, StatusCodeException, IOException;
		
		public void normalResult(Result result);
		
		public void abnormalResult(Result result);
		
		public void after(Result result);
		
		public void exception(Exception exception);
		
	}
	
};
