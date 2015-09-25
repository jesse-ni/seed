package com.g.seed.textresolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.util.Log;

public class GText
{
	private Document doc;
	private final List<RenderItem> items = new ArrayList<RenderItem>();
	
	public GText(String xml)
	{
		setContent(xml);
	}
	
	private void setContent(String xml)
	{
		try
		{
			this.doc = DocumentHelper.parseText("<GText>" + xml + "</GText>");
		} catch (DocumentException e) {
			Log.e(super.getClass().getName(), e.getMessage());
		}
	}
	
	public Spanned exe(String xml) {
		setContent(xml);
		return exe();
	}
	
	public Spanned exe() {
		String text = new RBase().exe(this.doc.getRootElement(), this.items);
		SpannableStringBuilder ssb = new SpannableStringBuilder(text);
		Collections.reverse(items);
		for (RenderItem item : items) {
			for (CharacterStyle unit : item.getStyles()) {
				ssb.setSpan(unit, item.getStart(), item.getEnd(), 33);
			}
		}
		return ssb;
	}
	
	public String getText() {
		return new RBase().exe(this.doc.getRootElement(), this.items);
	}
}
