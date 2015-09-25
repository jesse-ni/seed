/**
 * 
 */
package com.g.seed.adapter;

import java.lang.reflect.Constructor;
import java.util.List;

import com.g.seed.autowired.Params;
import com.g.seed.autowired.ViewManager;
import com.g.seed.view.IUpdateAble;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @ClassName: Adapter
 * @author zhigeng.ni
 * @date 2015年9月8日 上午11:12:55
 * @Description: TODO (描述作用)
 * 				
 */
public class MyAdapter<BeanType> extends BaseAdapter {
	
	public MyAdapter(Context context, List<BeanType> beans, Class<? extends View> viewClazz) {
		super();
		this.context = context;
		this.beanList = beans;
		try {
			constructor = viewClazz.getConstructor(Context.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	public MyAdapter(Context context, List<BeanType> beans, int layoutID) {
		super();
		this.context = context;
		this.beanList = beans;
		this.layoutID = layoutID;
	}
	
	private Context context;
	private List<BeanType> beanList;
	private Constructor<? extends View> constructor;
	private int layoutID;
	
	@Override
	public int getCount() {
		return beanList.size();
	}
	
	@Override
	public Object getItem(int position) {
		return beanList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final BeanType bean = beanList.get(position);
		if (convertView != null) {
			ViewManager.dataChange(convertView, bean);
			if (convertView instanceof IUpdateAble) {
				((IUpdateAble<BeanType>) convertView).update(bean);
			}
			return convertView;
		}
		return constructor != null ? createView(bean) : createViewFromLayoutID(bean);
	}
	
	private View createView(final BeanType bean) {
		try {
			return new ViewManager(constructor.newInstance(context), new Params(bean)).flate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private View createViewFromLayoutID(final BeanType bean) {
		return new ViewManager(context, layoutID, new Params(bean)).create();
	}
	
	/**
	 * @return the goodsList
	 */
	public List<BeanType> getBeanList() {
		return beanList;
	}
	
	/**
	 * @param goodsList the goodsList to set
	 */
	public void setBeanList(List<BeanType> goodsList) {
		this.beanList = goodsList;
	}
	
}
