package com.g.seed.web.exception;

public class ReflectToolException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public ReflectToolException()
  {
  }

  public ReflectToolException(String detailMessage, Throwable throwable)
  {
    super(detailMessage, throwable);
  }

  public ReflectToolException(String detailMessage) {
    super(detailMessage);
  }

  public ReflectToolException(Throwable throwable) {
    super(throwable);
  }
}