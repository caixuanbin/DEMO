package com.xbcai.myweb.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @classname LogController
 * @description TODO
 * @date 2019/2/14 20:43
 */
@RestController
@RequestMapping(value="/log")
@Slf4j
public class LogController {
    @GetMapping("/index")
    public String index(){
        log.info("这是 info级别的日志");
       return "index";
    }
    @GetMapping("/errorInfo")
    public String errorInfo(){
        log.error("这是errorInfo级别的日志");
        return "error";
    }
}
