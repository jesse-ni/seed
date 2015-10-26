/**
 * 
 */
package com.g.seed.util;

import com.g.seed.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

/**
 * @ClassName: MyDialog
 * @author zhigeng.ni
 * @date 2015年10月16日 上午11:05:45
 * @Description: TODO (描述作用)
 * 				
 */
public class MyDialog extends Dialog {
	
	public MyDialog(Context context) {
		super(context, R.style.AlertDialog);
		View view = getWindow().getLayoutInflater().inflate(R.layout.dialog_alert, null);
		setContentView(view);
		setCanceledOnTouchOutside(false);
		setCancelable(true);
	}
	
}
