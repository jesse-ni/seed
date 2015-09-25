package com.g.seed.web.result;

import java.io.InputStream;

public class StreamResult extends Result
{
  private static final long serialVersionUID = 1L;
  private InputStream istream;

  public StreamResult(String resultCode, String resultDesc) {
    super(resultCode, resultDesc);
  }

  public StreamResult(InputStream inputStream) {
    super("1", "success");
    this.istream = inputStream;
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
}