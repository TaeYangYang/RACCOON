package com.mycom.lacoon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("id", "user");
        return "main";
    }
}
