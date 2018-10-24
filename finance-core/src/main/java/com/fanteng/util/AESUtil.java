package com.fanteng.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	/**
	 * 生成AES密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getAESKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		String AESKey = EncryptUtil.encoderByBase64(secretKey.getEncoded());
		return AESKey;
	}

	/**
	 * 把AES秘钥转换成SecretKey对象
	 * 
	 * @param AESKey
	 * @return
	 * @throws Exception
	 */
	public static SecretKey loadAESKey(String AESKey) throws Exception {
		byte[] bytes = EncryptUtil.matchesByBase64(AESKey.getBytes());
		SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, "AES");
		return secretKeySpec;
	}

	/**
	 * 加密
	 * 
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encoder(byte[] source, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(source);
	}

	/**
	 * 解密
	 * 
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] matches(byte[] source, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(source);
	}

}
