package com.g.seed.web.exception;

import org.apache.http.StatusLine;

public class ServerException extends StatusCodeException
{
  private static final long serialVersionUID = 1L;

  public ServerException(StatusLine statusLine, String desc)
  {
    super(statusLine, desc);
  }
}