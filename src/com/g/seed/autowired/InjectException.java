package com.g.seed.autowired;

public class InjectException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public InjectException()
  {
  }

  public InjectException(String detailMessage, Throwable throwable)
  {
    super(detailMessage, throwable);
  }

  public InjectException(String detailMessage)
  {
    super(detailMessage);
  }

  public InjectException(Throwable throwable)
  {
    super(throwable);
  }
}