package com.g.seed.web.exception;

public class ParamException extends RuntimeException
{
  private static final long serialVersionUID = -2049111010215699655L;

  public ParamException()
  {
  }

  public ParamException(String detailMessage, Throwable throwable)
  {
    super(detailMessage, throwable);
  }

  public ParamException(String detailMessage)
  {
    super(detailMessage);
  }

  public ParamException(Throwable throwable)
  {
    super(throwable);
  }
}