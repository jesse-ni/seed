/**
 * 
 */
package com.g.seed.web.fieldFilter;

import java.util.Map;
import java.util.Map.Entry;

import com.g.seed.util.ReflectTool.FieldFiltrateInfo;
import com.g.seed.util.ReflectTool.IFieldFilter;
import com.g.seed.web.Encrypt;
import com.g.seed.web.IEncryptor;
import com.g.seed.web.exception.ParamException;

public class FieldFilterEncrypt implements IFieldFilter {
	public FieldFilterEncrypt(IEncryptor encryptor) {
		this.encryptor = encryptor;
	}
	
	private IEncryptor encryptor;
	
	@Override
	public boolean exe(FieldFiltrateInfo fi) throws IllegalAccessException, IllegalArgumentException,
			NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
		if (fi.field.isAnnotationPresent(Encrypt.class)) {
			if (encryptor == null)
				throw new ParamException("Detect the annotation Encryption but the encryptor is null!");
			Encrypt encryptInfo = fi.field.getAnnotation(Encrypt.class);
			if (fi.value instanceof Map<?, ?>) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) fi.value;
				for (Entry<String, Object> entry : map.entrySet()) {
					map.put(entry.getKey(), enc(entry.getValue(), encryptInfo));
				}
			} else {
				fi.value = enc(fi.value, encryptInfo);
			}
		}
		return true;
	}
	
	private String enc(Object value, Encrypt encryptInfo) {
		return encryptor.exe(String.valueOf(value), encryptInfo.value(), encryptInfo.ekeyType());
	}
}
