package com.g.seed.web.resultlistener;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

import com.g.seed.web.exception.DataException;
import com.g.seed.web.exception.StatusCodeException;
import com.g.seed.web.result.JsonResult;
import com.g.seed.web.result.Result;
import com.g.seed.web.resultprocessor.JsonResultProcessor;
import com.g.seed.web.task.MyAsyncTask;
import com.g.seed.web.task.MyAsyncTask.AsyncResultListener;

public class JsonResultListener implements AsyncResultListener {

	@Override
	public void before(MyAsyncTask task) {
		// TODO Auto-generated method stub

	}

	@Override
	public Result onResponse(HttpResponse response) throws ParseException, StatusCodeException, IOException {
		String contentType = response.getFirstHeader("Content-Type").getValue();
		if (contentType.startsWith("text")) {
			// Note:JSON content-type should be application/json
			return new JsonResultProcessor(response).exe();
		} else {
			throw new DataException("unsupported Content-Type:" + contentType, null);
		}
	}

	@Override
	public final void normalResult(Result result) {
		normalResult((JsonResult) result);
	}

	public void normalResult(JsonResult result) {
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
