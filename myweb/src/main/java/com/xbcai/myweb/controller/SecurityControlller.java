package com.xbcai.myweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityControlller {
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/main")
    public String main(){
        return "main";
    }
}
