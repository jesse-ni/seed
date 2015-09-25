/**
 * 
 */
package com.g.seed.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * @ClassName: ObjUtil
 * @author zhigeng.ni
 * @date 2015年9月7日 下午12:34:00
 * @Description: TODO (描述作用)
 * 				
 */
public class ObjUtil {
	
	public static Object deepCopy(Object o) throws Exception {
		return toObject(toByteArray(o));
	}
	
	private static Object toObject(final byte[] byteArray) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
			ObjectInputStream ois = new ObjectInputStream(bis);
			final Object readObject = ois.readObject();
			bis.close();
			ois.close();
			return readObject;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static byte[] toByteArray(Object o) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(o);
			byte[] byteArray = bos.toByteArray();
			bos.close();
			oos.close();
			return byteArray;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static boolean equals(Object a, Object b) {
		return Arrays.equals(toByteArray(a), toByteArray(b));
	}
}
