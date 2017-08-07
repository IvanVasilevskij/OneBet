package ru.onebet.exampleproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllersForAuthenticator {

    public ControllersForAuthenticator() {}

    @GetMapping("/toLoginPage")
    public String toLoginPage() {
        return "withoutrole/login";
    }

    @PostMapping("/signin")
    public String login() {
        return "free/home-page";
    }

}
