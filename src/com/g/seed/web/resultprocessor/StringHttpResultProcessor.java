package com.g.seed.web.resultprocessor;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public class StringHttpResultProcessor extends HttpResultProcessor<String>
{
  public StringHttpResultProcessor(HttpResponse httpResponse)
  {
    super(httpResponse);
  }

  protected String normalExe(HttpResponse httpResponse)
    throws ParseException, IOException
  {
    return EntityUtils.toString(httpResponse.getEntity());
  }

  protected String statusCodeExceptionDesc(HttpResponse httpResponse)
  {
    try {
      return EntityUtils.toString(httpResponse.getEntity()); } catch (Exception e) {
    }
    return "Unknow";
  }
}