package com.g.seed.textresolver;

import java.util.List;

import org.dom4j.Element;

public class RIf extends Resolver {
	@Override
	public String exe(Element element, int position, List<RenderItem> items) {
		String test = element.attribute("test").getValue();
		
		Element branchBlock = element.element(test);
		
		if ((branchBlock == null) && (Boolean.parseBoolean(test)))
			branchBlock = element;
		if (branchBlock == null)
			return "";
			
		Resolver resolver = ResolverFactory.create(test);
		return resolver.exe(branchBlock, position, items);
	}
}
