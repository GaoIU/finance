package com.fanteng.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class CustomMultipartResolver extends CommonsMultipartResolver {

	@Override
	public boolean isMultipart(HttpServletRequest request) {
		return (request != null && isMultipartContent(request));
	}

	public static final boolean isMultipartContent(HttpServletRequest request) {
		HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
		if (HttpMethod.POST != httpMethod && HttpMethod.PUT != httpMethod) {
			return false;
		}
		return FileUploadBase.isMultipartContent(new ServletRequestContext(request));
	}

}
