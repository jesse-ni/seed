package com.g.seed.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.g.seed.util.ReflectTool;
import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.web.exception.ParamException;

public class POTool
{
	public POTool(IEncryptor encryptor)
	{
		this.encryptor = encryptor;
	}
	
	public POTool() {}
	
	private IEncryptor encryptor;
	private ReflectTool.IFieldFilter fieldFilterOptional = new FieldFilterOptional();
	private ReflectTool.IFieldFilter fieldFilterEncrypt = new FieldFilterEncrypt();
	private ReflectTool.IFieldFilter fieldFilterName = new FieldFilterName();
	
	class FieldFilterOptional implements IFieldFilter {
		@Override
		public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
				NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
			return !(fi.field.isAnnotationPresent(Optional.class) && fi.value == null);
		}
	}
	
	class FieldFilterEncrypt implements IFieldFilter {
		@Override
		public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
				NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
			if (fi.field.isAnnotationPresent(Encrypt.class)) {
				if (encryptor == null)
					throw new ParamException("Detect the annotation Encryption but the encryptor is null!");
				Encrypt encryptInfo = fi.field.getAnnotation(Encrypt.class);
				fi.value = encryptor.exe(String.valueOf(fi.value), encryptInfo.value(), encryptInfo.ekeyType());
			}
			return true;
		}
	}
	
	class FieldFilterName implements IFieldFilter {
		@Override
		public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
				NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
			if (fi.field.isAnnotationPresent(Name.class)) {
				fi.name = fi.field.getAnnotation(Name.class).value();
			}
			return true;
		}
	}
	
	private ReflectTool reflectTool() {
		return new ReflectTool(fieldFilterOptional, fieldFilterEncrypt, fieldFilterName);
	}
	
	/**
	 * @Title: change
	 * @Description: TODO (将参数对象转为Post请求方式的参数列表)
	 * @param po
	 * @return
	 * @return List<NameValuePair>
	 * @throws
	 */
	public List<NameValuePair> change(Object... poarray) {
		final List<NameValuePair> result = new ArrayList<NameValuePair>();
		for (int i = 0; i < poarray.length && poarray[i] != null; i++) {
			reflectTool().catchAttr(poarray[i], new IFieldFilter() {
				
				@Override
				public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
						NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
					String name = fi.name;
					String value = String.valueOf(fi.value);
					result.add(new BasicNameValuePair(name, value));
					return true;
				}
			});
		}
		return result;
	}
	
	/**
	 * @Title: change
	 * @Description: TODO (将Map转为Post请求方式的参数列表)
	 * @param params
	 * @return
	 * @return List<NameValuePair>
	 * @throws
	 */
	public List<NameValuePair> change(Map<String, Object> params) {
		final List<NameValuePair> result = new ArrayList<NameValuePair>();
		if ((params != null) && !params.isEmpty()) {
			for (Entry<String, Object> param : params.entrySet()) {
				result.add(new BasicNameValuePair(param.getKey(), (String) param.getValue()));
			}
		}
		return result;
	}
	
	/**
	 * @Title: changeStr
	 * @Description: TODO (将参数对象转为Get请求方式的字符串参数列表)
	 * @param nParamObject
	 * @return
	 * @return String
	 * @throws
	 */
	public String changeStr(Object nParamObject) {
		final StringBuffer result = new StringBuffer();
		reflectTool().catchAttr(nParamObject, new IFieldFilter() {
			
			@Override
			public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
					NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
				String name = fi.name;
				result.append("&" + name + "=" + fi.value);
				return true;
			}
		});
		return result.toString().replaceFirst("&", "?");
	}
	
	/**
	 * @Title: changeStr
	 * @Description: TODO (将Map转为Get请求方式的字符串参数列表)
	 * @param params
	 * @return
	 * @return String
	 * @throws
	 */
	public String changeStr(Map<String, Object> params) {
		String result = "";
		if ((params != null) && !params.isEmpty()) {
			for (String unit : params.keySet()) {
				result = result + "&" + unit + "=" + params.get(unit);
			}
			result.replaceFirst("&", "?");
		}
		return result;
	}
	
	/**
	 * @Title: toString
	 * @Description: TODO (将参数对象转为字符串，不加密)
	 * @param po
	 * @return
	 * @return String
	 * @throws
	 */
	public String toString(Object po) {
		return toStringA(po, new ReflectTool(fieldFilterOptional, fieldFilterName));
	}
	
	/**
	 * @Title: toStringE
	 * @Description: TODO (将参数对象转为字符串，加密)
	 * @param po
	 * @return
	 * @return String
	 * @throws
	 */
	public String toStringE(Object po) {
		return toStringA(po, reflectTool());
	}
	
	/**
	 * @Title: toStringA
	 * @Description: TODO (将参数对象转为字符串)
	 * @param po
	 * @param pt
	 * @return
	 * @return String
	 * @throws
	 */
	public String toStringA(Object po, ReflectTool pt) {
		final StringBuffer sb = new StringBuffer(po.getClass().getSimpleName()).append(":").append("(");
		pt.catchAttr(po, new IFieldFilter() {
			
			@Override
			public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
					NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
				String name = fi.name;
				String value = String.valueOf(fi.value);
				sb.append(name).append(" = ").append(value).append(", ");
				return true;
			}
		});
		return sb.append(")").toString();
	}
	
	public IEncryptor getEncryptor() {
		return this.encryptor;
	}
	
	public void setEncryptor(IEncryptor encryptor) {
		this.encryptor = encryptor;
	}
	
	static abstract interface ICatchAttrListener
	{
		public abstract void onCatch(String paramString, Object paramObject);
	}
}
