实现过滤器有两种方式
一、采用全局配置方式
1、定义一个配置类FilterConfiguration在类上添加注解@Configuration
2、在配置类下面初始化一个FilterRegistrationBean
3、在FilterRegistrationBean里面设置过滤器及要拦截的URL
二、采用注解方式
1、定义一个全局过滤类GFilter
2、在GFilter类头上加上注解 @WebFilter(filterName="GFilter",urlPatterns="/*")
3、在驱动类MyWebApplication加上注解ServletComponentScan


注：如果有多个执行顺序，也可以指定过滤器的执行顺序