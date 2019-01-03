package com.xbcai.myweb.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = {java.lang.NullPointerException.class})
    public ModelAndView nullPointException(Exception e){
        System.out.println("--GlobalException--nullPointException--");
        ModelAndView mv = new ModelAndView();
        mv.addObject("error",e.toString());
        mv.setViewName("error/global_error");
        return mv;
    }
    @ExceptionHandler(value = {java.lang.ArithmeticException.class})
    public ModelAndView zeroException(Exception e){
        System.out.println("--GlobalException--zeroException--");
        ModelAndView mv = new ModelAndView();
        mv.addObject("error",e.toString());
        mv.setViewName("error/global_error");
        return mv;
    }
    @ExceptionHandler(value = {java.lang.RuntimeException.class})
    public ModelAndView otherException(Exception e){
        System.out.println("--GlobalException--otherException--");
        ModelAndView mv = new ModelAndView();
        mv.addObject("error","其他运行时异常："+e.toString());
        mv.setViewName("error/global_error");
        return mv;
    }

}
