package com.fanteng.finance.app.util;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.fanteng.util.DateUtil;
import com.fanteng.util.JsonUtil;
import com.fanteng.util.RSAUtil;
import com.fanteng.util.SignatureProperties;
import com.fanteng.util.StringUtil;

public class CommonUtil {

	private static final String PHONE_REG = "\\b(ip(hone|od)|android|opera m(ob|in)i|windows (phone|ce)|blackberry|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp|laystation portable)|nokia|fennec|htc[-_]|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

	private static final String IPAD_REG = "\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

	private static final Pattern PHONE_PAT = Pattern.compile(PHONE_REG, Pattern.CASE_INSENSITIVE);

	private static final Pattern IPAD_PAT = Pattern.compile(IPAD_REG, Pattern.CASE_INSENSITIVE);

	public static boolean isClient(String userAgent) {
		if (StringUtil.isBlank(userAgent)) {
			userAgent = "";
		}

		Matcher matcherPhone = PHONE_PAT.matcher(userAgent);
		Matcher matcherIpad = IPAD_PAT.matcher(userAgent);

		if (matcherPhone.find() || matcherIpad.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据token获取用户ID
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getUserIdByToken(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization");
		if (StringUtil.isBlank(token)) {
			return null;
		}

		String json = RSAUtil.matchesByPrivateKey(token, SignatureProperties.SERVER_PRIVATE_KEY);
		Map<?, ?> map = JsonUtil.fromJson(json, Map.class);

		return MapUtils.getString(map, "id");
	}

	/**
	 * 根据token获取用户账号
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getUserNameByToken(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization");
		if (StringUtil.isBlank(token)) {
			return null;
		}

		String json = RSAUtil.matchesByPrivateKey(token, SignatureProperties.SERVER_PRIVATE_KEY);
		Map<?, ?> map = JsonUtil.fromJson(json, Map.class);

		return MapUtils.getString(map, "userName");
	}

	/**
	 * 获取25位长的订单号
	 * 
	 * @return
	 */
	public static String createOrderNo() {
		String nowDateStr = DateUtil.toString(new Date(), "yyyyMMddHHmmss");
		StringBuffer sb = new StringBuffer(nowDateStr);
		sb.append(RandomStringUtils.randomNumeric(11));
		return sb.toString();
	}

}
