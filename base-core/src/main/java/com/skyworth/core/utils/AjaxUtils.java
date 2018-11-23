package com.skyworth.core.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.WebRequest;

/**
 * 
 * @author 王歌
 *
 */
public class AjaxUtils {
	
	private AjaxUtils() {
	}

	public static boolean isAjaxRequest(WebRequest webRequest) {
		String requestedWith = webRequest.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}
	
	public static boolean isAjaxRequest(HttpServletRequest webRequest) {
		String requestedWith = webRequest.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}

	public static boolean isAjaxUploadRequest(WebRequest webRequest) {
		return webRequest.getParameter("ajaxUpload") != null;
	}
	
	public static boolean isAjaxUploadRequest(HttpServletRequest webRequest) {
		return webRequest.getParameter("ajaxUpload") != null;
	}

}