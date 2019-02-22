package com.xbcai.myweb.server;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @classname MyTomcatWebServerCustomizer
 * @description TODO
 * @date 2019/2/22 11:14
 */
@Component
public class MyTomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        List<TomcatConnectorCustomizer> tomcatConnectorCustomizers = new ArrayList();
        tomcatConnectorCustomizers.add(new MyTomcatConnectorCustomizer());
        factory.setTomcatConnectorCustomizers(tomcatConnectorCustomizers);
    }
}
