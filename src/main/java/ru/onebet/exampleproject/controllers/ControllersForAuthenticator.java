package ru.onebet.exampleproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControllersForAuthenticator {

    public ControllersForAuthenticator() {}

    @GetMapping("/anonymous/to-login-page")
    public String toLoginPage() {
        return "withoutrole/login";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/signin")
    public String login() {
        return "withoutrole/login";
    }

}
