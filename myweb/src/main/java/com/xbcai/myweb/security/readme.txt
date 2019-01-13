实现一个springboot+springsecurity+springjpa 的登录验证，步骤如下
1、在pom.xml添加spring-boot-starter-security依赖
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
2、创建WebSecurityConfig.java继承WebSecurityConfigurerAdapter来配置security的访问权限及身份校验的一些规则
   我在这里配置了三道身份校验的链，如果只需要简单的数据库校验，只需要配置好实现了UserDetailsService接口的类，实现里面loadUserByUsername方法便可以；
   1）在WebSecurityConfig类上开启@EnableWebSecurity；
   2）将MyAuthenticationProvider、MyInMemoryAuthenticationProvider、MyUserDetailsService bean注入（三个类下面会创建）；
   3）初始化一个密码编码bean BCryptPasswordEncoder;
   4）重写 protected void configure(AuthenticationManagerBuilder auth)方法，将身份校验加入调用链；
   5）重写protected void configure(HttpSecurity http)方法，定义反馈权限
3、编写实体类SecurityUser只有简单的用户名、密码，及拥有的权限；
4、编写数据库操作类SecurityUserRepository实现简单的根据用户名查找用户的方法；
5、编写MyUserDetailsService实现UserDetailsService该接口，并覆盖loadUserByUsername方法，这里只是查询数据库将该用户信息封装成
   org.springframework.security.core.userdetails.User对象返回，这里用到的编码器是在WebSecurityConfig.java里面初始化的PasswordEncoder；
6、编写MyAuthenticationProvider实现AuthenticationProvider接口，实现对前端传入的身份的校验，如果authenticate返回null则交给下一个实现了AuthenticationProvider接口的类进行校验，
   该AuthenticationProvider接口的public boolean supports(Class<?> aClass)默认返回值是false,若想让该类被AuthenticationProvider支持，必须返回true,不然不会调用该类进行身份校验；
7、编写MyInMemoryAuthenticationProvider类实现AuthenticationProvider接口进行对前端传入用户校验，和第6步一样的原理；
8、编写SecurityControlller业务实现类，里面就只有简单的两个方法，在浏览器里面进行访问就可以实现了url的身份验证；


