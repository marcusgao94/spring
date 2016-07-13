package com.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by gy on 16/7/12.
 */

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage() {
        return "/static/index.html";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register() {
        return "/static/view/register.html";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "/static/view/login.html";
    }
}
