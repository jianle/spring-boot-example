package com.example.controller;

import com.example.interceptor.LoginInterceptor;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * Created by jianle on 17-9-12.
 */

@Controller
@RequestMapping("/")
public class LoginController {


    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FindByIndexNameSessionRepository<? extends ExpiringSession> sessionRepository;


    @Value("${server.session.max}")
    private int sessionMax;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginView() {
        return "/login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {

        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user != null) {

            Set<String> sessionIds = sessionRepository.findByIndexNameAndIndexValue(
                    FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username)
                    .keySet();

            if (sessionIds.size() >= sessionMax && !sessionIds.contains(session.getId())) {
                return "登录失败，用户已在其他设备上登录";
            }

            session.setAttribute(LoginInterceptor.USER_SESSION_PREFIX, user);
            session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);

            return "登录成功";
        } else {
            return "登录失败，用户名密码错误";
        }
    }

}
