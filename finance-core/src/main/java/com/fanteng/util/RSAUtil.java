package com.fanteng.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAUtil {

	/**
	 * 生成秘钥对
	 * 
	 * @return
	 * @throws Exception
	 */
	public static KeyPair getKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return keyPair;
	}

	/**
	 * 获取公钥
	 * 
	 * @param keyPair
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(KeyPair keyPair) throws Exception {
		PublicKey publicKey = keyPair.getPublic();
		String key = EncryptUtil.encoderByBase64(publicKey.getEncoded());
		return key;
	}

	/**
	 * 获取私钥
	 * 
	 * @param keyPair
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(KeyPair keyPair) throws Exception {
		PrivateKey privateKey = keyPair.getPrivate();
		String key = EncryptUtil.encoderByBase64(privateKey.getEncoded());
		return key;
	}

	/**
	 * 把公钥转换成PublicKey对象
	 * 
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static PublicKey toPublicKey(String publicKey) throws Exception {
		byte[] bytes = EncryptUtil.matchesByBase64(publicKey.getBytes());
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(x509EncodedKeySpec);
	}

	/**
	 * 把私钥转换成PrivateKey对象
	 * 
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey toPrivateKey(String privateKey) throws Exception {
		byte[] bytes = EncryptUtil.matchesByBase64(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	}

	/**
	 * 公钥加密
	 * 
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encoderByPublicKey(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	/**
	 * 公钥加密
	 * 
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encoderByPublicKey(String content, String publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, toPublicKey(publicKey));
		byte[] bytes = cipher.doFinal(content.getBytes());
		return new String(EncryptUtil.encoderByBase64(bytes));
	}

	/**
	 * 私钥解密
	 * 
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] matchesByPrivateKey(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	/**
	 * 私钥解密
	 * 
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String matchesByPrivateKey(String content, String privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, toPrivateKey(privateKey));
		byte[] bytes = cipher.doFinal(EncryptUtil.matchesByBase64(content.getBytes()));
		return new String(bytes);
	}

}
