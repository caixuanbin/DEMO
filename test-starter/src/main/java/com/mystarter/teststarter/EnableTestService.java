package com.mystarter.teststarter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Administrator
 * @classname EnableTestService
 * @description 需要在调用者的Main类加上该注解就能等效于spring.factories文件配置
 * @date 2019/2/18 14:19
 * @Import(TestServiceAutoConfiguration.class) 相当于使用定义spring.factories完成Bean的自动装配
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TestServiceAutoConfiguration.class)
public @interface EnableTestService {
}
