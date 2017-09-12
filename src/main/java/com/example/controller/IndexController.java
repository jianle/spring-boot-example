package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by jianle on 17-6-8.
 */

@Controller
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @RequestMapping("hello")
    @ResponseBody
    public String hello(HttpSession session) {

        UUID uid = UUID.randomUUID();
        return "hello world." + "[" + uid.toString() + "]";
    }

}
