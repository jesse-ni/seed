package com.g.seed.web.exception;

import org.apache.http.StatusLine;

public class NotFoundException extends StatusCodeException
{
  private static final long serialVersionUID = 1L;

  public NotFoundException(StatusLine statusLine, String desc)
  {
    super(statusLine, desc);
  }
}