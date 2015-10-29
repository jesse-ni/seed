package com.g.seed.web.resultprocessor;

import java.io.IOException;

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
		return new StreamResult(httpResponse.getEntity().getContent());
	}
	
	@Override
	protected String statusCodeExceptionDesc(HttpResponse httpResponse)
	{
		return "StreamResult execption";
	}
}
