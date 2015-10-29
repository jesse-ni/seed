package com.g.seed.util;

public abstract interface Delegate<ReturnType>
{
  public abstract ReturnType invoke();
}