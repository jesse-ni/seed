package com.g.seed.web.resultprocessor;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

public class StreamHttpResultProcessor extends HttpResultProcessor<InputStream>
{
	public StreamHttpResultProcessor(HttpResponse httpResponse)
	{
		super(httpResponse);
	}
	
	@Override
	protected InputStream normalExe(HttpResponse httpResponse)
			throws ParseException, IOException
	{
		return httpResponse.getEntity().getContent();
	}
	
	@Override
	protected String statusCodeExceptionDesc(HttpResponse httpResponse)
	{
		return "";
	}
}
