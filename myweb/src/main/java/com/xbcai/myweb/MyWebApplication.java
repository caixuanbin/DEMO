package com.xbcai.myweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MyWebApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyWebApplication.class, args);
    }

}

