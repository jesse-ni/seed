package com.g.seed.textresolver;

public class ResolverFactory
{
  public static Resolver create(String name)
  {
    if (name.equals("if"))
      return new RIf();
    if (name.equals("font"))
      return new RFont();
    if ((name.equals("false")) || (name.equals("true")))
      return new RBase();
    return null;
  }
}