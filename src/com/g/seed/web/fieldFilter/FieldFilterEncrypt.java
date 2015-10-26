/**
 * 
 */
package com.g.seed.web.fieldFilter;

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
			fi.value = encryptor.exe(String.valueOf(fi.value), encryptInfo.value(), encryptInfo.ekeyType());
		}
		return true;
	}
}