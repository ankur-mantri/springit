package com.vega.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
        model.addAttribute("message", "Hello World");

        return "Home";
    }
}
