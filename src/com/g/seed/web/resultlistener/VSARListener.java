package com.g.seed.web.resultlistener;

import android.content.Context;

import com.g.seed.web.result.Result;
import com.g.seed.web.task.MyAsyncTask;

/**
 * ViewShieldAsyncResultListener
 * 
 * @author zhigeng.ni
 *
 */
public class VSARListener extends JsonResultListener2 {

	public VSARListener(Context context) {
		super(context);
	}

	@Override
	public void before(MyAsyncTask task) {
//		LoadingDialog.show(getContext());
	}

	@Override
	public void after(Result result) {
//		LoadingDialog.close();
	}
}
