package com.g.seed.textresolver;

import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.AbstractNode;
import org.dom4j.tree.DefaultText;

public class RBase extends Resolver
{
	@Override
	public String exe(Element element, int position, List<RenderItem> items)
	{
		@SuppressWarnings("unchecked")
		List<AbstractNode> contentList = element.content();
		int i = 0;
		for (int cursor = 0; i < contentList.size(); ++i) {
			Object unit = contentList.get(i);
			if (unit instanceof Element) {
				Element subEle = (Element) unit;
				Resolver resolver = ResolverFactory.create(subEle.getName());
				String resolveResult = resolver.exe(subEle, position + cursor, items);
				contentList.set(i, new DefaultText(resolveResult));
			}
			cursor += ((DefaultText) contentList.get(i)).getText().length();
		}
		return element.getStringValue();
	}
	
	public String exe(Element element, List<RenderItem> items) {
		return exe(element, 0, items);
	}
}
