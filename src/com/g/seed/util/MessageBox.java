package com.g.seed.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class MessageBox {
	public static void show(Context context, String msg){
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public static void show(Context context, int resID){
		Toast toast = Toast.makeText(context, resID, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public static void show(Context context, String msg, int duration){
		Toast toast = Toast.makeText(context, msg, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public static void show(Context context, int resID, int duration){
		Toast toast = Toast.makeText(context, resID, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
}
