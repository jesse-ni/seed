package com.g.seed.autowired;

import com.g.seed.textresolver.EL;
import com.g.seed.textresolver.GText;
import com.g.seed.view.IFormElement;
import com.g.seed.view.IGTextAble;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class GViewFactory
		implements LayoutInflater.Factory {
	private EL el;
	private ExtendedAttribute viewHolder;
	private final LayoutInflater inflater;
	private static final String[] sClassPrefixList = { "android.widget.", "android.view.", "android.webkit.", "com.g.seed.view." };
	private int cacheID = 0;
	
	private static final String TAG = GViewFactory.class.getName();
	
	public GViewFactory(LayoutInflater inflater, EL el, ExtendedAttribute viewHolder) {
		this.inflater = inflater;
		this.el = el;
		this.viewHolder = viewHolder;
	}
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		try {
			return autowired(rendering(create(name, attrs)), attrs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private View autowired(View view, AttributeSet attrs) {
		if (!(view.getClass().isAnnotationPresent(Autowired.class)))
			return view;
		String elStr = getScopeKey(attrs, view);
		GIntent gintent = new GIntent(view);
		Params params = new Params(el.analyze(elStr == null ? "${bean}" : elStr));
		gintent.setParams(params);
		return new ViewManager(gintent).flate();
	}
	
	private View rendering(View view) {
		if (view instanceof IGTextAble) {
			final IGTextAble textAble = (IGTextAble) view;
			final String eled = el.exe(textAble.getExpression());
			final Spanned spanned = new GText(eled).exe();
			textAble.setText(spanned);
			textAble.setDA(ViewManager.cacheID, cacheID++);
			final Object bean = el.getGInterpreter().getParams().getBean();
			TextAbleR textAbleR = new TextAbleR(spanned, textAble.getExpression());
			ViewManager.cache(bean, textAble, textAbleR);
			viewHolder.add(textAble);
		}
		if (view instanceof IFormElement) {
			IFormElement formElement = (IFormElement) view;
			viewHolder.add(formElement);
		}
		return view;
	}
	
	private View create(String name, AttributeSet attrs)
			throws ClassNotFoundException {
		if (-1 != name.indexOf("."))
			return this.inflater.createView(name, null, attrs);
		for (String prefix : sClassPrefixList) {
			try {
				return this.inflater.createView(name, prefix, attrs);
			} catch (ClassNotFoundException localClassNotFoundException) {
			
			}
		}
		return null;
	}
	
	private String getScopeKey(AttributeSet attrs, View result) {
		String packageName;
		try {
			packageName = result.getContext().getPackageName();
			String classStyleable = packageName + ".R$styleable";
			int[] attrArrays = (int[]) Class.forName(classStyleable).getField("ViewManagerCallBack").get(null);
			int index = Class.forName(classStyleable).getField("ViewManagerCallBack_scope").getInt(null);
			TypedArray typedArray = result.getContext().obtainStyledAttributes(attrs, attrArrays);
			String elStr = typedArray.getString(index);
			typedArray.recycle();
			return elStr;
		} catch (Exception e) {
			Log.e(TAG, "getScopeKey fail >> " + e.toString());
		}
		return null;
	}
	
	public EL getEl() {
		return this.el;
	}
	
	public void setEl(EL el) {
		this.el = el;
	}
}
