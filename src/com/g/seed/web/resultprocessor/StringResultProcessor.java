package com.g.seed.web.resultprocessor;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.g.seed.web.exception.DataException;
import com.g.seed.web.result.StringResult;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class StringResultProcessor extends HttpResultProcessor<StringResult>
{
	public StringResultProcessor(HttpResponse httpResponse)
	{
		super(httpResponse);
	}
	
	@Override
	protected StringResult normalExe(HttpResponse httpResponse)
			throws ParseException, IOException
	{
		String string = EntityUtils.toString(httpResponse.getEntity());
		try {
			StringResult result = new Gson().fromJson(string, StringResult.class);
			result.setProtype(string);
			return result;
		} catch (JsonParseException e) {
			throw new DataException(e, string);
		}
	}
	
	@Override
	protected String statusCodeExceptionDesc(HttpResponse httpResponse)
	{
		try
		{
			return EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
		}
		return "Unknow";
	}
}
