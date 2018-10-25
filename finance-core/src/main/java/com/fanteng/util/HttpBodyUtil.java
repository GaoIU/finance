package com.fanteng.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpBodyUtil extends HttpServletRequestWrapper {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private byte[] body;

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (ArrayUtils.isEmpty(body)) {
			return super.getInputStream();
		}

		final ByteArrayInputStream bais = new ByteArrayInputStream(body);

		return new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}
		};
	}

	public byte[] getBody() {
		return body;
	}

	public HttpBodyUtil(HttpServletRequest request) {
		super(request);
		this.body = getBody(request);
	}

	public HttpBodyUtil(HttpServletRequest request, byte[] body) {
		super(request);
		this.body = body;
	}

	private byte[] getBody(ServletRequest request) {
		byte[] bytes = null;
		try {
			InputStream inputStream = request.getInputStream();
			bytes = IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("参数读取异常");
		}

		return bytes;
	}

}
