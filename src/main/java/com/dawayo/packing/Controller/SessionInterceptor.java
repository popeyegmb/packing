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

        // 로그인 페이지, 정적 리소스 등은 예외
        if (uri.startsWith("/login") || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/images")) {
            return true;
        }

        HttpSession session = request.getSession(false);
       
        if (session == null || session.getAttribute("id") == null) {
            
            response.sendRedirect("/login");
            return false; // 컨트롤러로 진입하지 않음
        }
   else{
     System.err.println(session.getAttribute("id"));
   }

        return true; // 세션 유효, 컨트롤러로 진행
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
