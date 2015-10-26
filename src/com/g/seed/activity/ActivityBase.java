package com.g.seed.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.g.seed.autowired.Injector;
import com.g.seed.autowired.Params;
import com.g.seed.autowired.ViewManager;
import com.g.seed.web.service.Enctype;
import com.g.seed.web.service.IForm;
import com.g.seed.web.service.Service;
import com.g.seed.web.task.MyAsyncTask.AsyncResultListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class ActivityBase extends Activity {
	
	private final List<OnDestroyListener> onDestroyListeners = new ArrayList<OnDestroyListener>();
	
	private Params params;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initialise(savedInstanceState);
		getWindow().takeKeyEvents(true);
	}
	
	protected void onCreate(Bundle savedInstanceState, int contentViewID) {
		initialise(savedInstanceState);
		params = new Params(getIntent().getExtras());
		params.setBean(getBean());
		setContentView(createView(contentViewID));
		new Injector(this, params).execute();
		getWindow().takeKeyEvents(true);
	}
	
	public View getRootView() {
		return ViewManager.getRootView(this);
	}
	
	public void update(Object bean) {
		ViewManager.dataChange(getRootView(), bean);
	}
	
	protected ActivityBase self() {
		return this;
	}
	
	protected void initialise(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected void hideStateBar() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	protected View flateView(View view) {
		return new ViewManager(view, params).flate();
	}
	
	protected View createView(int id) {
		return new ViewManager(this, id, params).create();
	}
	
	public Serializable getBean() {
		return getIntent().getSerializableExtra(Params.beanKey);
	}
	
	public String string(int resid) {
		return getResources().getString(resid);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		for (final OnDestroyListener unit : onDestroyListeners) {
			unit.onDestroy();
		}
	}
	
	public void startActivity(Class<? extends Activity> clazz, boolean suicide, int... flags) {
		startActivity(clazz, getBean(), suicide, flags);
	}
	
	public void startActivity(Class<? extends Activity> clazz, Serializable bean, boolean suicide, int... flags) {
		final Intent intent = new Intent(this, clazz);
		intent.putExtra(Params.beanKey, bean);
		if (flags != null) {
			for (final int unit : flags) {
				intent.addFlags(unit);
			}
		}
		startActivity(intent);
		if (suicide)
			finish();
	}
	
	public void startActivity(Class<? extends Activity> clazz) {
		startActivity(new Intent(this, clazz));
	}
	
	public boolean formCheck() {
		return ViewManager.formCheck(this);
	}
	
	public List<NameValuePair> buildPairs() {
		return ViewManager.buildPairs(this);
	}
	
	public Object buildPO(Class<?> clazz) {
		return ViewManager.buildPO(this, clazz);
	}
	
	public boolean buildPO(Object po) {
		return ViewManager.buildPO(this, po);
	}
	
	public void submit(IForm form, final AsyncResultListener l) {
		if (buildPO(form)) {
			if (form.getEnctype().equals(Enctype.urlencoded)) {
				Service.getInstance().asyncPost(form.getAction(), form, l);
			}else if (form.getEnctype().equals(Enctype.multipart)) {
				Service.getInstance().asyncPostMultipart(form.getAction(), form, l);
			}
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//		MessageBox.show(this, "got it");
		return super.dispatchTouchEvent(ev);
	}
	
	public void addOnDestroyListener(OnDestroyListener l) {
		onDestroyListeners.add(l);
	}
	
	public interface OnDestroyListener {
		void onDestroy();
	}
}
