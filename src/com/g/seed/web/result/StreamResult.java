package com.g.seed.web.result;

import java.io.InputStream;

public class StreamResult extends Result
{
  private static final long serialVersionUID = 1L;
  private InputStream istream;
  private long contentLength;

  public StreamResult(String resultCode, String resultDesc) {
    super(resultCode, resultDesc);
  }

  public StreamResult(InputStream inputStream, long contentLength) {
    super("1", "success");
    this.istream = inputStream;
    this.contentLength = contentLength;
    super.setProtype(inputStream);
  }

  public InputStream getStream()
  {
    return this.istream;
  }

  public String printProtype()
  {
    return "StreamResult:" + this.protype.toString();
  }
  
  public long getContentLength(){
	  return contentLength;
  }
}