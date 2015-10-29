package com.g.seed.textresolver;

import android.graphics.Color;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Element;

public class RFont extends RBase {
	
	@Override
	public String exe(Element element, int position, List<RenderItem> items) {
		String resolveResult = super.exe(element, position, items);
		items.add(new RenderItem(position, resolveResult.length(), getStyles(element)));
		return resolveResult;
	}
	
	private CharacterStyle[] getStyles(Element element) {
		CharacterStyle[] result = new CharacterStyle[element.attributes().size()];
		for (int i = 0; i < result.length; ++i) {
			Attribute attr = element.attribute(i);
			if (attr.getName().equals("color"))
				result[i] = new ForegroundColorSpan(Color.parseColor(attr.getValue()));
			else if (attr.getName().equals("size"))
				result[i] = new AbsoluteSizeSpan(Integer.parseInt(attr.getValue()));
			else if (attr.getName().equals("rsize"))
				result[i] = new RelativeSizeSpan(Float.parseFloat(attr.getValue()));
			else if (attr.getName().equals("bold"))
				result[i] = new StyleSpan(0);
			else if ((attr.getName().equals("mline")) &&
					(attr.getValue().equals("true")))
				result[i] = new StrikethroughSpan();
		}
		
		return result;
	}
}
