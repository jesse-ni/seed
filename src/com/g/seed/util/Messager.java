/**
 * 
 */
package com.g.seed.util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/** 
* @ClassName: Messager 
* @author zhigeng.ni 
* @date 2015年9月6日 下午6:08:07 
* @Description: TODO (描述作用) 
*  
*/
public class Messager {
	private Map<Object, List<MReceiver<?>>> map = new WeakHashMap<Object, List<MReceiver<?>>>();
	private static Messager instance = new Messager();
	
	public static synchronized Messager getInstance() {
		return instance;
	}
	
	public void send(String name, Data data) {
		for (List<MReceiver<?>> receivers : map.values()) {
			for (MReceiver<?> receiver : receivers) {
				if (receiver.getName().equals(name) && receiver.owner() != null)
					receiver.receive(data);
			}
		}
		
	}
	
	public void send(String name) {
		send(name, null);
	}
	
	public void send(String name, Object o) {
		send(name, new Data(o));
	}
	
	public void send(String name, Object key, Object value) {
		send(name, new Data(key, value));
	}
	
	public int cc() {
		int result = 0;
		for (List<MReceiver<?>> receivers : map.values()) {
			result += receivers.size();
		}
		return result;
	}
	
	public void putReceiver(MReceiver<?> receiver) {
		List<MReceiver<?>> receivers = map.get(receiver.owner());
		if (receivers == null) {
			receivers = new ArrayList<Messager.MReceiver<?>>();
			receivers.add(receiver);
			map.put(receiver.owner(), receivers);
		} else {
			receivers.add(receiver);
		}
	}
	
	public static abstract class MReceiver<Owner> {
		public MReceiver(Owner owner, String name) {
			this.name = name;
			this.owner = new WeakReference<Owner>(owner);
		}
		
		private String name;
		private WeakReference<Owner> owner;
		
		public Owner owner() {
			return owner.get();
		}
		
		public abstract void receive(Data data);
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class Data extends HashMap<Object, Object> {
		
		private static final long serialVersionUID = 1L;
		
		private Object o = null;
		
		public Data(Object key, Object value) {
			put(key, value);
		}
		
		public Data(Object o) {
			this.o = o;
		}
		
		public Data() {}
		
		public Object get() {
			return o;
		}
		
	}
	
}
