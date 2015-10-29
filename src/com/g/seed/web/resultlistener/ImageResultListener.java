package com.g.seed.web.resultlistener;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

import com.g.seed.web.exception.DataException;
import com.g.seed.web.exception.StatusCodeException;
import com.g.seed.web.result.Result;
import com.g.seed.web.resultprocessor.JsonResultProcessor;
import com.g.seed.web.resultprocessor.StreamResultProcessor;
import com.g.seed.web.task.MyAsyncTask;
import com.g.seed.web.task.MyAsyncTask.AsyncResultListener;

public class ImageResultListener implements AsyncResultListener {

	@Override
	public void before(MyAsyncTask task) {
		// TODO Auto-generated method stub

	}

	@Override
	public Result onResponse(HttpResponse response) throws ParseException, StatusCodeException, IOException {
		String contentType = response.getFirstHeader("Content-Type").getValue();
		if (contentType.startsWith("image")) {
			onOpenStream(response.getEntity().getContent());
			return new StreamResultProcessor(response).exe();
		} else if (contentType.startsWith("text")) {
			return new JsonResultProcessor(response).exe();
		} else {
			throw new DataException("unsupported Content-Type:" + contentType, null);
		}
	}

	public void onOpenStream(InputStream istream) throws IOException {

	}

	@Override
	public void normalResult(Result result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void abnormalResult(Result result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void after(Result result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exception(Exception e) {
		// TODO Auto-generated method stub

	}

}
