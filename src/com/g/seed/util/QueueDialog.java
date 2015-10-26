/**
 * 
 */
package com.g.seed.util;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;

public class QueueDialog {
	
	private static final String TAG = "QueueDialog";
	private static List<Token> tokens = new LinkedList<Token>();
	static MyDialog dialog;
	
	public synchronized static Token show(Context context, String tvRes) {
		return show(context, tvRes, true);
	}
	
	public synchronized static Token show(Context context, String tvRes, boolean cancelable) {
		Token token = new Token();
		if(tokens.isEmpty()){
			dialog = new MyDialog(context);
			dialog.show();
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					while (!QueueDialog.tokens.isEmpty()) {
						QueueDialog.tokens.remove(0).getsListener().onInvalid();
					}
				}
			});
		}
		tokens.add(token);
		return token;
	}
	
	public synchronized static void close(Token token) {
		if (tokens.contains(token)) {
			tokens.remove(token);
			if (tokens.isEmpty() && dialog.isShowing()) {
				dialog.cancel();
			}
		} else {
			Log.i(TAG, "invalid token-" + token.getName());
		}
	}
	
	public static class Token {
		private String id;
		private String name;
		private StateListener sListener = new StateListener() {
			public void onInvalid() {
			
			}
		};
		
		public Token() {
			id = String.valueOf(System.currentTimeMillis());
			this.name = this.id;
		}
		
		public Token(String name) {
			this.id = String.valueOf(new Date().getTime());
			this.name = name;
		}
		
		public String getID() {
			return id;
		}
		
		public void close() {
			QueueDialog.close(this);
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public StateListener getsListener() {
			return sListener;
		}
		
		public void setsListener(StateListener sListener) {
			this.sListener = sListener;
		}
		
		public interface StateListener {
			void onInvalid();
		}
		
	}
}
