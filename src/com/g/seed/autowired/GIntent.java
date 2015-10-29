package com.g.seed.autowired;

import android.content.Context;
import android.view.View;

public class GIntent
{
	private Context context;
	private View target;
	private Integer targetID;
	private Params params;
	
	public GIntent()
	{
		this.params = new Params();
	}
	
	public GIntent(Context context) {
		this();
		this.context = context;
	}
	
	public GIntent(View target) {
		this(target.getContext());
		this.target = target;
	}
	
	public GIntent(Context context, int targetID) {
		this(context);
		this.targetID = Integer.valueOf(targetID);
	}
	
	public Object getBean()
	{
		return this.params.getBean();
	}
	
	public Context getContext() {
		return this.context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public Params getParams() {
		return this.params;
	}
	
	public void addParam(String key, Object value) {
		this.params.put(key, value);
	}
	
	public void setParams(Params params) {
		this.params = params;
	}
	
	public Object getParam(String key) {
		return this.params.get(key);
	}
	
	public View getTarget() {
		return this.target;
	}
	
	public void setTarget(View target) {
		this.target = target;
	}
	
	public Integer getTargetID() {
		return this.targetID;
	}
	
	public void setTargetID(Integer targetID) {
		this.targetID = targetID;
	}
}
