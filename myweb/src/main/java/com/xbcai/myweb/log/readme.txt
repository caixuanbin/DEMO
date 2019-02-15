场景：定义logback日志插件，将正常日志，异常日志分开两个文件存储
1、在ideal中 File->Settings->Plugins 在marketplace下搜索lombok Plugin 插件点击安装(该插件是方便在程序中不用定义logger,直接在任何类中直接写log.info(),log.error 等)
2、在pom.xml文件中引入依赖
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
3、在resources下定义日志的相关信息，我这里定义为logback-spring.xml,具体内容就不列出，查看代码就知道
4、定义日志的存放路径及级别，在application-*.yml中填写
    log:
      path: D:/TEST/log/dev
      level: DEBUG
5、在controller层类上面写上注解@Slf4j,然后就直接写log.info log.error 日志进行日志记录
    @RestController
    @RequestMapping(value="/log")
    @Slf4j
    public class LogController {
        @GetMapping("/index")
        public String index(){
            log.info("这是 info级别的日志");
           return "index";
        }
        @GetMapping("/errorInfo")
        public String errorInfo(){
            log.error("这是errorInfo级别的日志");
            return "error";
        }
    }