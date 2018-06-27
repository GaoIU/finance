package com.fanteng.util;

import java.util.UUID;

public class UUIDUtil {

	private final static UUID uuid = UUID.randomUUID();

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return uuid.toString();
	}

	/**
	 * 获取消除 - 的UUID
	 * 
	 * @return
	 */
	public static String getSimpleUUID() {
		return uuid.toString().replaceAll("-", "");
	}

}
