package com.g.seed.textresolver;

import java.util.List;
import org.dom4j.Element;

public abstract class Resolver
{
  public abstract String exe(Element paramElement, int paramInt, List<RenderItem> paramList);
}