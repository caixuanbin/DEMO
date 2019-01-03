建立全局异常机制
1、建立全局异常类GlobalException
2、在类上添加注解@ControllerAdvice
3、针对每个异常定义对应的异常方法处理，例如：
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
4、在controller层如果抛出了对应的异常，就会被全局异常类所捕获，将异常信息显示在一个页面上

注：这里是将异常信息统一显示在templates\error 下面的global_error.html,可以根据实际情况而定，也可以将异常信息以json
形式返回到调用页面，springboot默认页面是放在resources/templates下面的,并且需要在项目中引入spring-boot-starter-thymeleaf依赖才能找到页面