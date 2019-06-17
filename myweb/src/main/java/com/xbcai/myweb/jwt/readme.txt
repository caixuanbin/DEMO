场景：使用springboot+springsecurity+jwt+springjpa 实现了一个restful api 接口的权限验证，
      也就是调用接口之前，先登录验证，后台返回token，以后每次调用接口都必须将该token放在请求头部带过来，如果验证通过，调用接口成功；
1、在pom.xml文件里引入springsecurity、jwt的jar包
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
2、创建用户实体类JwtUser，这里只有id,username,password三个属性；
3、数据库操作类JwtUserRepository实现简单的根据用户名查找用户的方法；
4、创建类JwtUserDetailService实现UserDetailsService接口，并覆盖loadUserByUsername方法，这里只是查询数据库将该用户信息封装成
org.springframework.security.core.userdetails.User对象返回，这里用到的编码器是在WebSecurityConfig.java里面初始化的PasswordEncoder；
5、创建类GrantedAuthorityImpl封装用户权限；
6、创建类CustomAuthenticationProvider实现AuthenticationProvider接口来进行登录自定义鉴权；
7、创建类ConstantKey保存公共调用属性，这里只定义了jwt的密钥；
8、创建类JWTAuthenticationFilter继承BasicAuthenticationFilter来进行权限的拦截，校验头部是否有token，进行token解析验证，如果验证通过，接口调用成功，如果验证不通过，必须先进行登录拿到token;
9、创建类JwtController进行自定义登录检验的controller层，登录成功，将生成token放再头部返回给客户端，客户端下次调用其他接口必须带上token值来进行检验
   （为了方便，这里也定义了要给用户注册的接口，让用户先注册一个账号，然后以该账号登录获取token）
10、创建类JwtWebSecurityConfig继承WebSecurityConfigurerAdapter配置权限验证的相关规则，在类上面开启@EnableWebSecurity验证；
11、在启动类里面开启@ServletComponentScan注解


