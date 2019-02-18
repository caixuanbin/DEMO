package com.xbcai.myweb.customautoconfiguration;

import com.mystarter.teststarter.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @classname TestController
 * @description TODO
 * @date 2019/2/18 11:07
 */
@RestController
public class TestController {
    @Autowired
    private TestService testService;
    @GetMapping("/autoTest")
    public String test(){
        testService.printConfInfo();
        return "OK";
    }
}
