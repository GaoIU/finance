package com.fanteng.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Blob;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {

	/**
	 * UTF-8 编码格式
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * 空字符
	 */
	public static final String EMPTY = "";

	/**
	 * 下划线字符
	 */
	public static final char UNDERLINE = '_';

	private StringUtil() {
	}

	/**
	 * <p>
	 * 拼接字符串第二个字符串第一个字母大写
	 * </p>
	 *
	 * @param concatStr
	 * @param str
	 * @return
	 */
	public static String concatCapitalize(String concatStr, final String str) {
		if (isEmpty(concatStr)) {
			concatStr = EMPTY;
		}
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}

		final char firstChar = str.charAt(0);
		if (Character.isTitleCase(firstChar)) {
			// already capitalized
			return str;
		}

		StringBuilder sb = new StringBuilder(strLen);
		sb.append(concatStr);
		sb.append(Character.toTitleCase(firstChar));
		sb.append(str.substring(1));
		return sb.toString();
	}

	/**
	 * <p>
	 * 字符串下划线转驼峰格式
	 * </p>
	 *
	 * @param param 需要转换的字符串
	 * @return 转换好的字符串
	 */
	public static String underlineToCamel(String param) {
		if (isEmpty(param)) {
			return EMPTY;
		}
		String temp = param.toLowerCase();
		int len = temp.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = temp.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(temp.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * Blob 转为 String 格式
	 * </p>
	 *
	 * @param blob Blob 对象
	 * @return
	 */
	public static String blob2String(Blob blob) {
		if (null != blob) {
			try {
				byte[] returnValue = blob.getBytes(1, (int) blob.length());
				return new String(returnValue, UTF8);
			} catch (Exception e) {
				throw new RuntimeException("Blob Convert To String Error!");
			}
		}
		return null;
	}

	/**
	 * 判断object是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (isBlank(obj.toString())) {
			return true;
		}

		if (equals(obj.toString(), "[]")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断object是否不为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		if (obj == null) {
			return false;
		}

		String string = obj.toString();
		if (isBlank(string)) {
			return false;
		}

		if (equals(string, "[]")) {
			return false;
		}
		return true;
	}

	/**
	 * 获取Exception异常信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionMsg(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

}