package com.g.seed.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.g.seed.SeedContext;
import com.g.seed.util.ReflectTool;
import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.web.fieldFilter.FieldFilterComponent;
import com.g.seed.web.fieldFilter.FieldFilterEncrypt;
import com.g.seed.web.fieldFilter.FieldFilterHeader;
import com.g.seed.web.fieldFilter.FieldFilterHeaders;
import com.g.seed.web.fieldFilter.FieldFilterIgnore;
import com.g.seed.web.fieldFilter.FieldFilterMap;
import com.g.seed.web.fieldFilter.FieldFilterName;
import com.g.seed.web.fieldFilter.FieldFilterOptional;
import com.g.seed.web.service.IParamPository;

public class POTool {
	public POTool() {}
	
	private ReflectTool.IFieldFilter fieldFilterOptional = new FieldFilterOptional();
	private ReflectTool.IFieldFilter fieldFilterEncrypt = new FieldFilterEncrypt(SeedContext.encryptor);
	private ReflectTool.IFieldFilter fieldFilterName = new FieldFilterName();
	private ReflectTool.IFieldFilter fieldFilterIgnore = new FieldFilterIgnore();
	private ReflectTool reflectTool;
	
	public ReflectTool buildReflectTool(IParamPository paramPository) {
		return reflectTool = new ReflectTool(
				fieldFilterIgnore,
				fieldFilterOptional,
				fieldFilterEncrypt,
				fieldFilterName,
				new FieldFilterHeader(paramPository),
				new FieldFilterMap(paramPository),
				new FieldFilterComponent(this, paramPository),
				new FieldFilterHeaders(this, paramPository));
	}
	
	public ReflectTool buildUnencryptedReflectTool(IParamPository paramPository) {
		buildReflectTool(paramPository).getFilters().remove(fieldFilterEncrypt);
		return reflectTool;
	}
	
	/**
	 * @Title: change @Description: TODO (将参数对象转为Post请求方式的参数列表) @param
	 *         po @return @return List<NameValuePair> @throws
	 */
	public List<NameValuePair> change(HttpMessage httpMessage, Object... poarray) {
		final List<NameValuePair> result = new ArrayList<NameValuePair>();
		changeParam(new ParamPositoryForPost(result, httpMessage), poarray);
		return result;
	}
	
	public void changeToMultipart(final MultipartEntityBuilder multipartEntityBuilder, HttpMessage httpMessage, Object... poarray) {
		changeParam(new ParamPositoryForMultipartEntity(multipartEntityBuilder, httpMessage), poarray);
	}
	
	public void putHeaders(HttpMessage httpMessage, Object object) {
		changeParam(new ParamPositoryHeader(httpMessage), object);
	}
	
	/**
	 * @Title: changeStr @Description: TODO (将参数对象转为Get请求方式的字符串参数列表) @param
	 *         nParamObject @return @return String @throws
	 */
	public String changeStr(HttpMessage httpMessage, Object nParamObject) {
		final StringBuffer result = new StringBuffer();
		changeParam(new ParamPositoryForGet(result, httpMessage), nParamObject);
		return result.toString().replaceFirst("&", "?");
	}
	
	/**
	 * @Title: toString @Description: TODO (将参数对象转为字符串，不加密) @param
	 *         po @return @return String @throws
	 */
	public String toString(Object po) {
		final StringBuffer result = toStringHead(po);
		final ParamPositoryForDebugInfo paramPository = new ParamPositoryForDebugInfo(result);
		changeParam(buildUnencryptedReflectTool(paramPository), paramPository, po);
		return result.append("}").toString();
	}
	
	/**
	 * @Title: toStringE @Description: TODO (将参数对象转为字符串，加密) @param
	 *         po @return @return String @throws
	 */
	public String toStringE(Object po) {
		final StringBuffer result = toStringHead(po);
		changeParam(new ParamPositoryForDebugInfo(result), po);
		return result.append("}").toString();
	}
	
	public void changeParam(final IParamPository paramPository, Object... poarray) {
		changeParam(buildReflectTool(paramPository), paramPository, poarray);
	}
	
	/** 将原有的reflectTool给paramPository使用 */
	public void changeParam2(final IParamPository paramPository, Object... poarray) {
		changeParam(reflectTool, paramPository, poarray);
	}
	
	public void changeParam(ReflectTool reflectTool, final IParamPository paramPository, Object... poarray) {
		for (int i = 0; i < poarray.length && poarray[i] != null; i++) {
			reflectTool.catchAttr(poarray[i], new IFieldFilter() {
				
				@Override
				public boolean exe(FieldFiltrateInfo fi) throws Exception {
					paramPository.add(fi.name, fi.value);
					return true;
				}
			});
		}
	}
	
	/**
	 * @Title: change @Description: TODO (将Map转为Post请求方式的参数列表) @param
	 *         params @return @return List<NameValuePair> @throws
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
	 * @Title: changeStr @Description: TODO (将Map转为Get请求方式的字符串参数列表) @param
	 *         params @return @return String @throws
	 */
	public String changeStr(Map<String, Object> params) {
		String result = "";
		if ((params != null) && !params.isEmpty()) {
			for (String unit : params.keySet()) {
				result = result + "&" + unit + "=" + params.get(unit);
			}
			result = result.replaceFirst("&", "?");
		}
		return result;
	}
	
	private StringBuffer toStringHead(Object po) {
		return new StringBuffer(po.getClass().getSimpleName()).append(" ").append("{\n");
	}
	
	static abstract interface ICatchAttrListener {
		public abstract void onCatch(String paramString, Object paramObject);
	}
}
