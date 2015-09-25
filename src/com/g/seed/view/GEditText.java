/**
 * 
 */
package com.g.seed.view;

import org.apache.http.NameValuePair;

import com.g.seed.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * @ClassName: GEditText
 * @author zhigeng.ni
 * @date 2015年9月2日 上午11:17:09
 * @Description: TODO (描述作用)
 * 				
 */
public class GEditText extends EditText implements IFormElement {
	public GEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public GEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		final TypedArray typedArrayCheckAble = getContext().obtainStyledAttributes(attrs, R.styleable.CheckAble);
		this.err = typedArrayCheckAble.getString(R.styleable.CheckAble_err);
		this.rex = typedArrayCheckAble.getString(R.styleable.CheckAble_rex);
		typedArrayCheckAble.recycle();
	}
	
	public GEditText(Context context) {
		super(context);
	}
	
	private String err;
	private String rex;
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					check();
				}
			}
		});
	}
	
	public boolean check() {
		if (rex == null) { return true; }
		boolean result = getText().toString().matches(rex);
		if (!result && err != null) {
			setError(err);
		} else {
			setError(null);
		}
		return result;
	}
	
	public String value() {
		return String.valueOf(getText());
	}

	@Override
	public boolean isNecessary() {
		return false;
	}

	@Override
	public NameValuePair build() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}
}
