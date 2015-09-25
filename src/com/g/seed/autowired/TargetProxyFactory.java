package com.g.seed.autowired;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class TargetProxyFactory
{
	public static TargetProxy proxy(final Activity activity)
	{
		return new TargetProxy()
		{
			@Override
			public View findViewById(int id)
			{
				return activity.findViewById(id);
			}
			
			@Override
			public Context getContext()
			{
				return activity;
			}
			
			@Override
			public Object real()
			{
				return activity;
			}
		};
	}
	
	public static TargetProxy proxy(final View view)
	{
		return new TargetProxy()
		{
			@Override
			public View findViewById(int id)
			{
				return view.findViewById(id);
			}
			
			@Override
			public Context getContext()
			{
				return view.getContext();
			}
			
			@Override
			public Object real()
			{
				return view;
			}
		};
	}
}
