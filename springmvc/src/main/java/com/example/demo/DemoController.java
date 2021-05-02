package com.example.demo;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {
    @RequestMapping(value = {"/", "/home"})
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/hello")
    public String hello(Authentication authentication, Model model) {
        // 認証情報を取得
        UserModel userModel = (UserModel)authentication.getPrincipal();
        model.addAttribute("name", userModel.getName());

        return "hello";
    }
}
