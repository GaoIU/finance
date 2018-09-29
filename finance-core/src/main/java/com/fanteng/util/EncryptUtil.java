package com.fanteng.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EncryptUtil {

	private static Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

	private final static Log LOGGER = LogFactory.getLog(EncryptUtil.class);

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String encoderByMD5(CharSequence str) throws Exception {
		if (str == null || str.length() == 0) {
			return null;
		}

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] btInput = str.toString().getBytes();
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		mdInst.update(btInput);
		byte[] md = mdInst.digest();
		int j = md.length;
		char strs[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			strs[k++] = hexDigits[byte0 >>> 4 & 0xf];
			strs[k++] = hexDigits[byte0 & 0xf];
		}

		return new String(strs);
	}

	/**
	 * BC加密
	 * 
	 * @param rawPassword
	 * @return
	 */
	public static String encodeByBC(CharSequence rawPassword) {
		int strength = -1;
		if (strength != -1 && (strength < BCrypt.MIN_LOG_ROUNDS || strength > BCrypt.MAX_LOG_ROUNDS)) {
			throw new IllegalArgumentException("Bad strength");
		}
		String salt;
		if (strength > 0) {
			salt = BCrypt.gensalt(strength);
		} else {
			salt = BCrypt.gensalt();
		}
		return BCrypt.hashpw(rawPassword.toString(), salt);
	}

	/**
	 * BC加密验证
	 * 
	 * @param rawPassword
	 *            需要验证的原始密码
	 * @param encodedPassword
	 *            BC加密过的密码
	 * @return
	 */
	public static boolean matchesByBC(CharSequence rawPassword, String encodedPassword) {
		if (encodedPassword == null || encodedPassword.length() == 0) {
			LOGGER.warn("Empty encoded password");
			return false;
		}

		if (rawPassword == null || rawPassword.length() == 0) {
			LOGGER.warn("Empty encoded password");
			return false;
		}

		if (!BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
			LOGGER.warn("Encoded password does not look like BCrypt");
			return false;
		}

		return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
	}

	/**
	 * Base64加密
	 * 
	 * @param cs
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encoderByBase64(CharSequence cs) throws UnsupportedEncodingException {
		if (cs == null || cs.length() == 0) {
			return null;
		}
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(cs.toString().getBytes("UTF-8"));
	}

	/**
	 * Base64加密
	 * 
	 * @param bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encoderByBase64(byte[] bytes) throws UnsupportedEncodingException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(bytes);
	}

	/**
	 * Base64解密
	 * 
	 * @param cs
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String matchesByBase64(CharSequence cs) throws UnsupportedEncodingException {
		if (cs == null || cs.length() == 0) {
			return null;
		}
		Decoder decoder = Base64.getDecoder();
		byte[] decode = decoder.decode(cs.toString().getBytes("UTF-8"));
		return new String(decode, "UTF-8");
	}

	/**
	 * Base64解密
	 * 
	 * @param bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] matchesByBase64(byte[] bytes) throws UnsupportedEncodingException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		Decoder decoder = Base64.getDecoder();
		return decoder.decode(bytes);
	}

	/**
	 * Base64加密验证
	 * 
	 * @param cs
	 *            需要验证的原始密码
	 * @param encoder
	 *            Base64加密过的密码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean matchesByBase64(CharSequence cs, String encoder) throws UnsupportedEncodingException {
		if (cs == null || cs.length() == 0) {
			return false;
		}

		if (StringUtil.isEmpty(encoder)) {
			return false;
		}

		Decoder decoder = Base64.getDecoder();
		byte[] decode = decoder.decode(encoder.getBytes("UTF-8"));

		return StringUtil.equals(cs, new String(decode, "UTF-8"));
	}

}