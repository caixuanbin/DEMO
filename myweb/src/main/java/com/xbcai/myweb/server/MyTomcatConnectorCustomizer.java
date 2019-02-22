package com.xbcai.myweb.server;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;

/**
 * @author Administrator
 * @classname MyTomcatConnectorCustomizer
 * @description TODO
 * @date 2019/2/22 11:15
 */
public class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
    @Override
    public void customize(Connector connector) {
        Http11NioProtocol protocol =(Http11NioProtocol) connector.getProtocolHandler();
        protocol.setConnectionTimeout(7000);
    }
}
