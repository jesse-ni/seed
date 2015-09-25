package com.g.seed.autowired;

import android.content.Context;
import android.view.View;

public abstract interface TargetProxy
{
	public abstract View findViewById(int id);
	
	public abstract Context getContext();
	
	public abstract Object real();
}
