package com.green.shopping.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component("/interceptor")
public class AuthInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle (HttpServletRequest request,
                              HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();

        String requestUrl = request.getRequestURL().toString();

        if(requestUrl.contains("/login")) {
            return true;
        }
        // ------------------

        Object obj = session.getAttribute("login");
        if ( obj == null ) {
            response.sendRedirect("/login");
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}


