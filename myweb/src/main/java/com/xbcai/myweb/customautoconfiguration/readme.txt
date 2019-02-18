采用springboot自动装配策略自定义starter jar包供其他项目依赖使用
1、创建一个springboot 项目test-starter
   在ideal 开发工具中点击 file->project-Spring Initializr，点击下一步
   填写group:com.mystarter ;Artifact:test-starter 其他默认点击下一步
   根据实际情况选择相关依赖，我这里选择Core右边没有选 一直点击下一步直到完成
2、将启动项及test 包下面的东西及pom.xml下面的<build></build>删掉，用不到
3、创建服务类，这里是TestService
    public class TestService {
        private String ip;
        private int port;

        public TestService(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }
        public void printConfInfo(){
            System.out.println("IP:"+ip+",PORT:"+port);
        }
    }
4、创建TestServiceProperties类绑定配置属性
    @ConfigurationProperties(prefix = "xbcai-config")// 取配置文件前缀为xbcai-config配置
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
5、创建自动配置类TestServiceAutoConfiguration
    @Configuration
    @ConditionalOnClass(TestService.class)//存在TestService这个类才装配当前类
    //配置文件存在这个xbcai-config.enabled=true才启动，允许不存在该配置
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
6、创建注解EnableTestService
    /**
     * @description 需要在调用者的Main类加上该注解就能等效于spring.factories文件配置
     * @Import(TestServiceAutoConfiguration.class) 相当于使用定义spring.factories完成Bean的自动装配
     */
    @Inherited
    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Import(TestServiceAutoConfiguration.class)
    public @interface EnableTestService {
    }
7、在resouces包下创建application.properties文件，配置自动装配的默认绑定值，这里也可以不写，也可以写在代码里面
8、将test-starter install 打包
9、在新工程里面的pom.xml文件里面引入该jar包，我这里是在myweb项目里面加上
        <dependency>
            <groupId>com.mystarter</groupId>
            <artifactId>test-starter</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
10、在myweb项目启动类里面加上注解@EnableTestService
    @SpringBootApplication
    @ServletComponentScan
    @EnableTestService
    public class MyWebApplication {
        public static void main(String[] args) {
            SpringApplication.run(MyWebApplication.class, args);
        }
    }
11、在application-*.yml里面我这里是application-dev.yml里面配置自动装载的属性
    xbcai-config:
      host: 192.168.0.1
      port: 8888
      enabled: true
12、在controller类里面将test-starter jar包里面bean注入，就可以正常引用了
    @RestController
    public class TestController {
        @Autowired
        private TestService testService;
        @GetMapping("/autoTest")
        public String test(){
            testService.printConfInfo();
            return "OK";
        }
    }
13、项目启动后，访问autoTest就可以看到自动装配已经起作用了

 注：如果不想通过注解@EnableTestService启动，而是通过引入jar包就可以正常启动，就要在test-starter项目里的resources下新建
     META-INF文件夹，在下面新建spring.factories,在里面填写
     org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.mystarter.teststarter.TestServiceAutoConfiguration
     这个时候，就不需要EnableTestService注解类了，当引用引入test-starter包时，就已经将该自动配置加载类引入了
