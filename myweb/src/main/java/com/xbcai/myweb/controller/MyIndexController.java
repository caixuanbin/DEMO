package com.xbcai.myweb.controller;

import com.xbcai.myweb.aop.ControllerLogs;
import com.xbcai.myweb.model.User;
import com.xbcai.myweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
public class MyIndexController {
    @Autowired
    private UserService userService;
    @GetMapping("/myController")
    @ControllerLogs(description = "控制层获取用户信息。。。")
    public Object myController(@NotEmpty(message = "name is required") String name,
                               @NotEmpty(message = "job is required") String job){
        return userService.findObject(name,job);
    }
    @GetMapping("/myController2")
    @ControllerLogs(description = "控制层获取用户信息2...")
    public Object myController2(String name,String job){
        return new User(name,job);
    }
    @GetMapping("/myService")
    public Object myService(String name,String job){
        return userService.findObject2(name,job);
    }
    @GetMapping("/queryAll")
    public Object queryAll(){
        System.out.println("---queryAll---");
        User user =new User();
        user.setJob("程序员");
        user.setName("李四");
        return user;
    }

    @GetMapping("/queryOne")
    public Object queryOne(){
        System.out.println("---queryOne---");
        User user =new User();
        user.setJob("程序员2");
        user.setName("李四2");
        return user;
    }

    @GetMapping("/findOne")
    public Object findOne(){
        return "xbcai";
    }

    @GetMapping("/nullException")
    public String nullException(){
        String s =null;
        if(s.equals("a")){
            System.out.println("异常处理...");
        }else{
            System.out.println("没有异常...");
        }
        return "";
    }
    @GetMapping("/zeroException")
    public String zeroException(){
        System.out.println("1MyIndexController---zeroException---");
        int i=1/0;
        System.out.println("2MyIndexController---zeroException---");
        return "";
    }
    @GetMapping("/otherException")
    public String otherException(){
        throw  new RuntimeException();
    }
}
