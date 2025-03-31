package com.bryansiegel.ccsdbidsjava.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping("/")
    public String index() {
        return "public/index";
    }
}
