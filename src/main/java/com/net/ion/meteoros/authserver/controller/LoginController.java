package com.net.ion.meteoros.authserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController
{
    @GetMapping("/login")
    public String login(HttpServletRequest request)
    {
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess (@RequestParam(name="username") String username,
                   @RequestParam(name="password") String password)
    {
        return "login-success";
    }
}
