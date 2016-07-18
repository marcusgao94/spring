package com.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gy on 16/7/18.
 */

@Controller
public class ForumController {

    @RequestMapping("/secure/forum")
    public @ResponseBody String forum() {
        return "forum";
    }
}
