package com.ppmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * 2019/5/8
 * com.ppmall.util
 */
@Slf4j
public class CookieUtil {
	private final static String COOKIE_DOMAIN = "ppmall.com";
	private final static String COOKIE_NAME = "login_token";

	/**
	 * 写 Cookie
	 *
	 * @param response response
	 * @param token    token
	 */
	public static void writeLoginToken(HttpServletResponse response, String token) {
		Cookie cookie = new Cookie(COOKIE_NAME, token);
		cookie.setDomain(COOKIE_DOMAIN);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 365);
		log.info("write cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
		response.addCookie(cookie);
	}

	/**
	 * 读 Cookie
	 *
	 * @param request request
	 * @return Cookie's value
	 */
	public static String readLoginToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				log.info("read cookieName :{},cookieValue:{}", cookie.getName(), cookie.getValue());
				if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
					log.info("return CookieName:{},CookieValue:{}", cookie.getName(), cookie.getValue());
					return cookie.getValue();
				}
			}
		}
		return null;
	}


	/**
	 * 删除 Cookie
	 *
	 * @param request  request
	 * @param response response
	 */
	public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
					cookie.setDomain(COOKIE_DOMAIN);
					cookie.setPath("/");
					cookie.setMaxAge(0);
					log.info("delete cookieName:{} cookieValue:{}", cookie.getName(), cookie.getValue());
					response.addCookie(cookie);
					return;
				}
			}
		}

	}


}
