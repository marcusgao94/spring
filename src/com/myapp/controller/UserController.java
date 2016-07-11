package com.myapp.controller;

import com.myapp.model.User;
import com.myapp.service.UserService;
import com.myapp.service.UserServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gy on 16/7/8.
 */
@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    private static final Log logger = LogFactory
            .getLog(UserController.class);

    @RequestMapping(value = "/register")
    public String register() {
        logger.info("register called");
        return "/static/view/register.html";
    }

    @RequestMapping(value="/user/save", method = RequestMethod.POST)
    public @ResponseBody String saveUser(User user) {
        userService.saveUser(user);
        return "ProductView";
    }
}
