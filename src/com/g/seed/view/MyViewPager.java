package com.g.seed.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyViewPager extends ViewPager {
	
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MyViewPager(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec,
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
			if (h > height)
				height = h;
		}
		
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
				MeasureSpec.EXACTLY);
				
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	PointF downPoint = new PointF();
	OnSingleTouchListener onSingleTouchListener;
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		switch (evt.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downPoint.x = evt.getX();
			downPoint.y = evt.getY();
			if (this.getChildCount() > 1) {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (this.getChildCount() > 1) {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_UP:
			if (PointF.length(evt.getX() - downPoint.x, evt.getY()
					- downPoint.y) < (float) 5.0) {
				onSingleTouch(this);
				return true;
			}
			break;
		}
		return super.onTouchEvent(evt);
	}
	
	public void onSingleTouch(View v) {
		if (onSingleTouchListener != null) {
			onSingleTouchListener.onSingleTouch(v);
		}
	}
	
	public interface OnSingleTouchListener {
		public void onSingleTouch(View v);
	}
	
	public void setOnSingleTouchListener(
			OnSingleTouchListener onSingleTouchListener) {
		this.onSingleTouchListener = onSingleTouchListener;
	}
	
}
