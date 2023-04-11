package com.h10.sideproject.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CookieUtil {
    public static void createCookie(HttpServletResponse response, String name, String value, boolean secure, int maxAge, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(secure);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
