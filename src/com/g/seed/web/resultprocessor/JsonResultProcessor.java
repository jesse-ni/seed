package com.g.seed.web.resultprocessor;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.g.seed.web.exception.DataException;
import com.g.seed.web.result.JsonResult;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class JsonResultProcessor extends HttpResultProcessor<JsonResult> {
	public JsonResultProcessor(HttpResponse httpResponse) {
		super(httpResponse);
	}
	
	private static IJsonResultMaker jsonResultMaker;
	
	@Override
	protected JsonResult normalExe(HttpResponse httpResponse) throws ParseException, IOException {
		String string = EntityUtils.toString(httpResponse.getEntity());
		try {
			JsonResult result = null;
			if (jsonResultMaker != null) {
				result = jsonResultMaker.make(string);
			} else {
				result = new Gson().fromJson(string, JsonResult.class);
			}
			result.setProtype(string);
			return result;
		} catch (JsonParseException e) {
			throw new DataException(e, string);
		}
	}
	
	@Override
	protected String statusCodeExceptionDesc(HttpResponse httpResponse) {
		try {
			return EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			return "Unknow";
		}
	}
	
	public static interface IJsonResultMaker {
		JsonResult make(String json);
	}
	
	public static void setJsonResultMaker(IJsonResultMaker jsonResultMaker) {
		JsonResultProcessor.jsonResultMaker = jsonResultMaker;
	}
}
