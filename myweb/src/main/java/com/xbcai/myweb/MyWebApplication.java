package com.xbcai.myweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

@SpringBootApplication
@ServletComponentScan
public class MyWebApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyWebApplication.class, args);
    }

    public void test(){
        Model m = new ExtendedModelMap();
    }


}

