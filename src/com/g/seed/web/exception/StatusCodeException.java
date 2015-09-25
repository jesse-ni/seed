package com.g.seed.web.exception;

import org.apache.http.StatusLine;

public class StatusCodeException extends Exception
{
  private static final long serialVersionUID = 1L;
  private StatusLine statusLine;

  public StatusCodeException(StatusLine statusLine, String desc)
  {
    super(desc);
    this.statusLine = statusLine;
  }

  public StatusLine getStatusLine()
  {
    return this.statusLine;
  }
}