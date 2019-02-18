package com.mystarter.teststarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @classname TestServiceAutoConfiguration
 * @description TODO
 * @date 2019/2/18 14:05
 * @ConditionalOnClass(TestService.class) 存在TestService这个类才装配当前类
 * 配置文件存在这个xbcai-config.enabled=true才启动，允许不存在该配置
 */
@Configuration
@ConditionalOnClass(TestService.class)
@ConditionalOnProperty(name = "xbcai-config.enabled",havingValue = "true",matchIfMissing = true)
@EnableConfigurationProperties(TestServiceProperties.class)
public class TestServiceAutoConfiguration {
    /**
     * 没有TestService这个类才进行 装配
     */
    @Bean
    @ConditionalOnMissingBean
    public TestService testService(TestServiceProperties testServiceProperties){
        return new TestService(testServiceProperties.getHost(),testServiceProperties.getPort());
    }
}
