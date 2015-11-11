package com.g.seed.web.resultprocessor;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

import com.g.seed.web.result.StreamResult;

public class StreamResultProcessor extends HttpResultProcessor<StreamResult>
{
	public StreamResultProcessor(HttpResponse httpResponse)
	{
		super(httpResponse);
	}
	
	@Override
	protected StreamResult normalExe(HttpResponse httpResponse)
			throws ParseException, IOException
	{
		final HttpEntity entity = httpResponse.getEntity();
		return new StreamResult(entity.getContent(), entity.getContentLength());
	}
	
	@Override
	protected String statusCodeExceptionDesc(HttpResponse httpResponse)
	{
		return "StreamResult execption";
	}
}
