package com.mystarter.teststarter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Administrator
 * @classname TestServiceProperties
 * @description TODO
 * @date 2019/2/18 14:03
 * @ConfigurationProperties(prefix = "xbcai-config") 取配置文件前缀为xbcai-config配置
 */
@ConfigurationProperties(prefix = "xbcai-config")
public class TestServiceProperties {
    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
