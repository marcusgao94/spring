package com.myapp.controller;

import com.myapp.config.Constant;
import com.myapp.model.User;
import com.myapp.service.UserService;
import com.myapp.service.UserServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gy on 16/7/8.
 */
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    private static final Log logger = LogFactory
            .getLog(UserController.class);

    @RequestMapping(value="/user/save", method = RequestMethod.POST)
    public Boolean saveUser(@RequestBody User user) {
        user.setRole(Constant.COMMON_USER);
        return userService.saveUser(user);
    }
}
