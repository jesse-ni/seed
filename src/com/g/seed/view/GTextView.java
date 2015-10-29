/**
 * 
 */
package com.g.seed.view;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.g.seed.R;
import com.g.seed.textresolver.EL;
import com.g.seed.textresolver.GText;
import com.g.seed.web.EKeyType;
import com.g.seed.web.EncryptType;
import com.g.seed.web.IEncryptor;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @ClassName: GTextView
 * @author zhigeng.ni
 * @date 2015年8月17日 下午4:55:49
 * @Description: TODO (描述作用)
 * 				
 */
public class GTextView extends TextView implements IGTextAble, IFormElement {
	
	public GTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		final TypedArray typedArrayGTextAble = getContext().obtainStyledAttributes(attrs, R.styleable.GTextAble);
		this.expression = typedArrayGTextAble.getString(R.styleable.GTextAble_expression);
		typedArrayGTextAble.recycle();
		final TypedArray typedArrayCheckAble = getContext().obtainStyledAttributes(attrs, R.styleable.CheckAble);
		this.err = typedArrayCheckAble.getString(R.styleable.CheckAble_err);
		this.rex = typedArrayCheckAble.getString(R.styleable.CheckAble_rex);
		typedArrayCheckAble.recycle();
		final TypedArray typedArrayFormElement = getContext().obtainStyledAttributes(attrs, R.styleable.FormElement);
		this.name = typedArrayFormElement.getString(R.styleable.FormElement_name);
		this.encrypt = typedArrayFormElement.getString(R.styleable.FormElement_encrypt);
		this.optional = typedArrayFormElement.getString(R.styleable.FormElement_optional);
		typedArrayFormElement.recycle();
	}
	
	public GTextView(Context context) {
		super(context);
	}
	
	private IEncryptor myEncryptor;
	private String expression;
	private String err;
	private String rex;
	private String name;
	private String encrypt;
	private String ekeytype;
	private String optional;
	private Map<String, Object> dynamicAttribute = new HashMap<String, Object>();
	
	@Override
	public void setDA(String name, Object value) {
		dynamicAttribute.put(name, value);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getDA(String name) {
		return (T) dynamicAttribute.get(name);
	}
	
	@Override
	public void setExpression(String string) {
		this.expression = string;
	}
	
	@Override
	public void update(Object bean) {
		this.setText(new GText(new EL(bean).exe(expression)).exe());
	}
	
	@Override
	public void update(EL el) {
		this.setText(new GText(el.exe(expression)).exe());
	}
	
	@Override
	public String getExpression() {
		return expression;
	}
	
	@Override
	public boolean check() {
		if (TextUtils.isEmpty(rex)) { return true; }
		boolean result = getText().toString().matches(rex);
		if (!result && !TextUtils.isEmpty(err)) {
			setError(err);
		} else {
			setError(null);
		}
		return result;
	}
	
	@Override
	public String value() {
		return getText().toString();
	}
	
	@Override
	public NameValuePair build() {
		if (TextUtils.isEmpty(encrypt)) {
			return new BasicNameValuePair(name, value());
		} else {
			final EncryptType encryptType = EncryptType.valueOf(encrypt);
			final EKeyType keyType = TextUtils.isEmpty(ekeytype) ? EKeyType.PublicKey : EKeyType.valueOf(ekeytype);
			return new BasicNameValuePair(name, myEncryptor.exe(value(), encryptType, keyType));
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean isNecessary() {
		return !TextUtils.isEmpty(optional) && value() != null;
	}
	
}
