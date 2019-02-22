自定义内嵌tomcat配置信息
下面设置tomcat的连接超时时间为7000
1、创建MyTomcatConnectorCustomizer实现TomcatConnectorCustomizer覆盖customize方法设置自定义tomcat配置的相关信息，我这里只设置setConnectionTimeout
    public class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
        @Override
        public void customize(Connector connector) {
            Http11NioProtocol protocol =(Http11NioProtocol) connector.getProtocolHandler();
            protocol.setConnectionTimeout(7000);
        }
    }
2、创建MyTomcatWebServerCustomizer实现WebServerFactoryCustomizer，在该类上添加注解@Component，覆盖customize，将滴1步自定义的tomcat配置类设置进去
    @Component
    public class MyTomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
        @Override
        public void customize(TomcatServletWebServerFactory factory) {
            List<TomcatConnectorCustomizer> tomcatConnectorCustomizers = new ArrayList();
            tomcatConnectorCustomizers.add(new MyTomcatConnectorCustomizer());
            factory.setTomcatConnectorCustomizers(tomcatConnectorCustomizers);
        }
    }
到此，设置内嵌tomcat的配置已经结束，可以采用Jmeter软件测试随便的访问层，当大量线程访问时，如果tomcat连接不上的时候超过7000，就会断开连接，这样子后台的服务就不会挂掉
