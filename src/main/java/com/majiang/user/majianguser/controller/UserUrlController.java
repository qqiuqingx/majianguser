package com.majiang.user.majianguser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserUrlController {

    @RequestMapping("/main")
    public String getMain(){
        return "login";
    }

}
