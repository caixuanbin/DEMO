package com.xbcai.myweb.aop;

import com.alibaba.fastjson.JSON;
import com.xbcai.myweb.utils.IpUtils;
import com.xbcai.myweb.utils.StringUtils;
import com.xbcai.myweb.utils.UserAgentUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //Service层切点
    @Pointcut("@annotation(com.xbcai.myweb.aop.ServiceLogs)")
    public void serviceAspect() {
        System.out.println("--LogAspect--serviceAspect--");
    }
    //controller层切点
    @Pointcut("@annotation(com.xbcai.myweb.aop.ControllerLogs)")
    public void controllerAspect(){
        System.out.println("--LogAspect--controllerAspect--");
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            //类名
            String className = joinPoint.getTarget().getClass().getName();
            //请求方法
            String method =  joinPoint.getSignature().getName() + "()";
            //方法参数
            String methodParam = JSON.toJSONString(joinPoint.getArgs());
            Map<String, String[]> params = request.getParameterMap();
            String decode = "";
            //针对get请求
            if(request.getQueryString()!=null){
                try {
                    decode = URLDecoder.decode(request.getQueryString(),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else{
                //针对post请求
                for (String key : params.keySet()) {
                    String[] values = params.get(key);
                    for (int i = 0; i < values.length; i++) {
                        String value = values[i];
                        decode += key + "=" + value + "&";
                    }
                }
            }
            //将String根据&转成Map
            Map<String, Object> methodParamMap = transStringToMap(decode, "&", "=");
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //方法描述
            String methodDescription = getControllerMethodDescription(joinPoint);
            StringBuilder sb = new StringBuilder(1000);
            sb.append("\n");
            sb.append("*********************************Request请求***************************************");
            sb.append("\n");
            sb.append("ClassName     :  ").append(className).append("\n");
            sb.append("RequestMethod :  ").append(method).append("\n");
            sb.append("ContentType   :  ").append(("".equals(request.getContentType()) || request.getContentType() == null)?"FROM":request.getContentType()).append("\n");
            sb.append("RequestParams :  ").append(("".equals(decode) || decode == null)?methodParam:methodParamMap).append("\n");
            sb.append("RequestType   :  ").append(request.getMethod()).append("\n");
            sb.append("Description   :  ").append(methodDescription).append("\n");
            sb.append("ServerAddr    :  ").append(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()).append("\n");
            sb.append("RemoteAddr    :  ").append(IpUtils.getRemoteAddr(request)).append("\n");
            UserAgent userAgent = UserAgentUtils.getUserAgent(request);
            sb.append("DeviceName    :  ").append(userAgent.getOperatingSystem().getName()).append("\n");
            sb.append("BrowserName   :  ").append(userAgent.getBrowser().getName()).append("\n");
            sb.append("UserAgent     :  ").append(request.getHeader("User-Agent")).append("\n");
            sb.append("RequestUri    :  ").append(StringUtils.abbr(request.getRequestURI(), 255)).append("\n");
            sb.append("**************************");
            sb.append(df.format(new Date()));
            sb.append("***********************************");
            sb.append("\n");
            logger.info(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @AfterReturning(returning = "ret",pointcut = "controllerAspect()")
    public void doAfterReturning(Object ret){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //请求方法
        String method = StringUtils.abbr(request.getRequestURI(), 255);
        StringBuilder sb = new StringBuilder(1000);
        // 处理完请求，返回内容
        sb.append("\n");
        sb.append("Result        :  ").append(ret);
        logger.info(sb.toString());
    }
    @AfterThrowing(throwing = "ex",pointcut = "serviceAspect()")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable ex){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //类名
            String className = joinPoint.getTarget().getClass().getName();
            //请求方法
            String method =  (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
            //方法参数
            String methodParam = Arrays.toString(joinPoint.getArgs());
            //方法描述
            String methodDescription = getServiceMthodDescription(joinPoint);
            //获取用户请求方法的参数并序列化为JSON格式字符串
            String params = "";
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
                }
            }
            StringBuilder sb = new StringBuilder(1000);
            sb.append("\n");
            sb.append("*********************************Service异常***************************************");
            sb.append("\n");
            sb.append("ClassName        :  ").append(className).append("\n");
            sb.append("Method           :  ").append(method).append("\n");
            sb.append("Params           :  ").append("[" + params + "]").append("\n");
            sb.append("Description      :  ").append(methodDescription).append("\n");
            sb.append("ExceptionName    :  ").append(ex.getClass().getName()).append("\n");
            sb.append("ExceptionMessage :  ").append(ex.getMessage()).append("\n");
            logger.info(sb.toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     *  获取注解中对方法的描述信息 用于service层注解
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMthodDescription (JoinPoint joinPoint) throws Exception{
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        System.out.println("targetName:"+targetName);
        System.out.println("methodName:"+methodName);
        System.out.println("arguments:"+arguments);
        System.out.println("targetClass:"+targetClass);
        System.out.println("methods:"+methods);
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ServiceLogs.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception{
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        System.out.println("targetName:"+targetName);
        System.out.println("methodName:"+methodName);
        System.out.println("arguments:"+arguments);
        System.out.println("targetClass:"+targetClass);
        System.out.println("methods:"+methods);
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ControllerLogs.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * String 转Map
     * @param mapString 待转的String
     * @param separator 分割符
     * @param pairSeparator 分离器
     * @return
     */
    public static Map<String, Object> transStringToMap(String mapString, String separator, String pairSeparator) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] fSplit = mapString.split(separator);
        for (int i = 0; i < fSplit.length; i++) {
            if (fSplit[i]==null||fSplit[i].length()==0) {
                continue;
            }
            String[] sSplit = fSplit[i].split(pairSeparator);
            String value = fSplit[i].substring(fSplit[i].indexOf('=') + 1, fSplit[i].length());
            map.put(sSplit[0], value);
        }
        return map;
    }


}
