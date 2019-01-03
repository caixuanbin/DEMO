Spring Boot自定义注解+AOP实现日志记录
1、编写自定义注解例如：ControllerLogs和ServiceLogs
2、定义横切面类LogAspect
3、自定义切面例如：
@Pointcut("@annotation(com.xbcai.myweb.aop.ControllerLogs)")
    public void controllerAspect(){
        System.out.println("--LogAspect--controllerAspect--");
    }
 @Pointcut("@annotation(com.xbcai.myweb.aop.ServiceLogs)")
    public void serviceAspect() {
        System.out.println("--LogAspect--serviceAspect--");
    }
4、编写前置通知 用于拦截Controller层记录用户的操作
  @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){}
5、编写后置通知，用于拦截调用方法返回的信息
@AfterReturning(returning = "ret",pointcut = "controllerAspect()")
    public void doAfterReturning(Object ret){ }
6、编写异常通知，拥有拦截抛出的异常信息
@AfterThrowing(throwing = "ex",pointcut = "serviceAspect()")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable ex){}
7、在controller层（不一定是controller层）在需要拦截的方法的地方加上自定义的注解
例如：
 @GetMapping("/myController")
    @ControllerLogs(description = "控制层获取用户信息。。。")
    public Object myController(@NotEmpty(message = "name is required") String name,
                               @NotEmpty(message = "job is required") String job){
        return userService.findObject(name,job);
    }
8、异常的注解拦截器可以加在service层,如果有异常抛出便会触发LogAspect类中的doAfterThrowing方法打印异常信息
 @ServiceLogs(description = "service 层 查询用户信息")
    @Override
    public Object findObject2(String name, String job) {
        int i = 1/0;
        return new User(name,job);
    }

注：这里的pointcut指向的是自定义的注解，所以注解了该注解的方法就会被拦截

