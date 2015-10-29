package com.g.seed.web.resultprocessor;

import com.g.seed.web.exception.NotFoundException;
import com.g.seed.web.exception.ServerException;
import com.g.seed.web.exception.StatusCodeException;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;

public abstract class HttpResultProcessor<ResultType>
{
  protected HttpResponse httpResponse;

  public HttpResultProcessor()
  {
  }

  public HttpResultProcessor(HttpResponse httpResponse)
  {
    this.httpResponse = httpResponse;
  }

  public ResultType exe(HttpResponse httpResponse)
    throws ParseException, StatusCodeException, IOException
  {
    this.httpResponse = httpResponse;
    return exe();
  }

  public ResultType exe() throws StatusCodeException, ParseException, IOException
  {
    StatusLine statusLine = this.httpResponse.getStatusLine();
    if (statusLine.getStatusCode() == 200)
      return normalExe(this.httpResponse);
    if (statusLine.getStatusCode() == 500)
      throw new ServerException(statusLine, statusCodeExceptionDesc(this.httpResponse));
    if (statusLine.getStatusCode() == 404)
      throw new NotFoundException(statusLine, statusCodeExceptionDesc(this.httpResponse));

    throw new StatusCodeException(statusLine, statusCodeExceptionDesc(this.httpResponse));
  }

  protected abstract ResultType normalExe(HttpResponse paramHttpResponse)
    throws ParseException, IOException;

  protected String statusCodeExceptionDesc(HttpResponse httpResponse)
  {
    return "Unknow";
  }
}