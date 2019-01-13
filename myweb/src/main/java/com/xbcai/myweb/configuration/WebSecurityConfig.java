package com.xbcai.myweb.configuration;

import com.xbcai.myweb.security.MyAuthenticationProvider;
import com.xbcai.myweb.security.MyInMemoryAuthenticationProvider;
import com.xbcai.myweb.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;
    @Autowired
    private MyInMemoryAuthenticationProvider myInMemoryAuthenticationProvider;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("--WebSecurityConfig--configure auth");
        //添加数据库查询返回UserDetails，由默认的DaoAuthenticationProvider进行身份验证，
        // 也可以自己继承该类进行验证（DaoAuthenticationProvider该验证是在所有自定义验证最后执行，
        // 在这里的执行链是myAuthenticationProvider->myInMemoryAuthenticationProvider->DaoAuthenticationProvider）
        auth.userDetailsService(myUserDetailsService);
        //添加身份验证，如果myAuthenticationProvider--authenticate返回null则继续交给下一个AuthenticationProvider)进行验证，
        // 如果返回填充的authenticate则身份验证结束
        auth.authenticationProvider(myAuthenticationProvider);
        auth.authenticationProvider(myInMemoryAuthenticationProvider);//添加身份验证 一样的道理
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("WebSecurityConfig--configure http");
        http.authorizeRequests()
               .antMatchers("/resources/**", "/test/**","/query**").permitAll()
               .antMatchers("/main").hasRole("USERS")//这里大小写敏感，对应数据库的权限为ROLE_USERS
              // .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
               .anyRequest().authenticated()
              // .and().formLogin().loginPage("/login").permitAll()//指定自己的登陆页面
               .and().httpBasic();
      /** http.logout()
               .logoutUrl("/my/logout")
               .logoutSuccessUrl("/my/index")
               //Let’s you specify a custom LogoutSuccessHandler. If this is specified, logoutSuccessUrl() is ignored
               .logoutSuccessHandler(null)//logoutSuccessHandler
               .invalidateHttpSession(true)
               .addLogoutHandler(null)//logoutHandler
               //Allows specifying the names of cookies to be removed on logout success.
               // This is a shortcut for adding a CookieClearingLogoutHandler explicitly.
               .deleteCookies(null);//cookieNamesToClear
       **/
    }
}
