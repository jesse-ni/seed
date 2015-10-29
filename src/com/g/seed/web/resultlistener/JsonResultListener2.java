package com.g.seed.web.resultlistener;

import android.content.Context;
import android.widget.Toast;

import com.g.seed.util.MessageBox;
import com.g.seed.web.result.Result;

public class JsonResultListener2 extends JsonResultListener {
	private Context context;
	
	public JsonResultListener2(Context context) {
		this.setContext(context);
	}
	
	@Override
	public void abnormalResult(Result result) {
		MessageBox.show(getContext(), result.getResultDesc(), Toast.LENGTH_LONG);
	}
	
	public Context getContext() {
		return context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
}
