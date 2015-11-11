package com.g.seed.web.resultlistener;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

import com.g.seed.web.exception.StatusCodeException;
import com.g.seed.web.result.Result;
import com.g.seed.web.result.StreamResult;
import com.g.seed.web.resultprocessor.StreamResultProcessor;
import com.g.seed.web.task.MyAsyncTask;
import com.g.seed.web.task.MyAsyncTask.AsyncResultListener;

public class StreamResultListener implements AsyncResultListener {

	@Override
	public void before(MyAsyncTask task) {

	}

	@Override
	public Result onResponse(HttpResponse response) throws ParseException, StatusCodeException, IOException {
		final StreamResult result = new StreamResultProcessor(response).exe();
		resultInBackground(result);
		return result;
	}

	public void resultInBackground(StreamResult result) throws IOException {

	}

	@Override
	public void normalResult(Result result) {

	}

	@Override
	public void abnormalResult(Result result) {

	}

	@Override
	public void after(Result result) {

	}

	@Override
	public void exception(Exception e) {

	}

}
