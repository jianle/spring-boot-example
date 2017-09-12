package com.example.interceptor;

import com.example.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by jianle on 17-9-12.
 */

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    public static final String USER_SESSION_PREFIX = "user";

    @Override
    public boolean preHandle(HttpServletRequest request
            , HttpServletResponse response, Object handler) throws Exception {

        // check user exists
        HttpSession session = request.getSession();
        Object object = session.getAttribute(USER_SESSION_PREFIX);

        if (object != null && object instanceof User) {
            return true;
        }

        response.sendRedirect("/login");
        return false;
    }
}
