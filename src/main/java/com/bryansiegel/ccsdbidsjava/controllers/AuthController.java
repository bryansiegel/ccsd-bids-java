package com.bryansiegel.ccsdbidsjava.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/login-error/")
    public String loginError() {
        return "admin/login-error";
    }

    @GetMapping("admin/dashboard/")
    public String dashboard(Authentication authentication, Model model) {
        model.addAttribute("user", authentication.getName());

        return "admin/dashboard";
    }
}
