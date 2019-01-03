package com.xbcai.myweb.aop;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLogs {
    // 描述
    String description() default "";
}
