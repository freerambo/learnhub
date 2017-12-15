package com.rambo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String single() {
        return "single";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }
}