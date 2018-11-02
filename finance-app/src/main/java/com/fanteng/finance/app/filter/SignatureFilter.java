package com.fanteng.finance.app.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import com.fanteng.core.HttpStatus;
import com.fanteng.finance.app.properties.SignatureProperties;
import com.fanteng.finance.app.util.CommonUtil;
import com.fanteng.util.AESUtil;
import com.fanteng.util.HttpBodyUtil;
import com.fanteng.util.JsonUtil;
import com.fanteng.util.RSAUtil;
import com.fanteng.util.StringUtil;

public class SignatureFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String userAgent = req.getHeader("User-Agent");
		if (StringUtil.isBlank(userAgent)) {
			error(res);
			return;
		}

		if (CommonUtil.isClient(userAgent)) {
			String signature = req.getHeader("Signature");
			if (StringUtil.isBlank(signature)) {
				error(res);
				return;
			}

			String body = null;
			HttpBodyUtil httpBodyUtil = null;
			try {
				String aesKey = RSAUtil.matchesByPrivateKey(signature, SignatureProperties.SERVER_PRIVATE_KEY);
				httpBodyUtil = new HttpBodyUtil(req);
				Map<?, ?> map = JsonUtil.fromJson(new String(httpBodyUtil.getBody()), Map.class);
				body = AESUtil.matches(MapUtils.getString(map, "content"), aesKey);
			} catch (Exception e) {
				e.printStackTrace();
				error(res);
				return;
			}

			if (StringUtil.isBlank(body)) {
				error(res);
				return;
			}
			httpBodyUtil = new HttpBodyUtil(req, body.getBytes());
			chain.doFilter(httpBodyUtil, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

	private void error(HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpStatus.NOT_ACCEPTABLE);
		PrintWriter writer = response.getWriter();
		writer.write("{\"code\": 406, \"msg\": \"无效签名\"}");
	}

}
