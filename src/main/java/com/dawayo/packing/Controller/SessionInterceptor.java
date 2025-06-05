package com.dawayo.packing.Controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionInterceptor implements HandlerInterceptor {

@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String uri = request.getRequestURI();
    System.err.println("Request URI: " + uri);

    // 로그인 페이지, 정적 리소스 등은 예외 처리
    if (uri.startsWith("/login") || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/images")) {
        return true;
    }

    HttpSession session = request.getSession(false);

    // 세션이 없거나 로그인되지 않은 경우
    if (session == null || session.getAttribute("id") == null) {
        System.err.println("No session or not logged in. Redirecting to /login");
        response.sendRedirect("/login");
        return false;
    }

    String userId = String.valueOf(session.getAttribute("id"));
    System.err.println("User ID from session: " + userId);

    // 관리자 페이지 접근 제어
    if (uri.startsWith("/admin")) {
        if ("2".equals(userId)) {
            System.err.println("Admin access granted for user: " + userId);
            return true; // 관리자 접근 허용
        } else {
            System.err.println("Admin access denied for user: " + userId);
            response.sendRedirect("/"); // 일반 유저 접근 시 홈으로 리디렉션
            return false;
        }
    }

    // 일반 유저 접근 허용
    return true;
}


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                        ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("id") != null) {
                modelAndView.addObject("loginUser", session.getAttribute("id"));
            }
        }
    }

}
