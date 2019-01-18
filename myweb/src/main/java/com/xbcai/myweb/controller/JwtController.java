package com.xbcai.myweb.controller;

import com.xbcai.myweb.jwt.ConstantKey;
import com.xbcai.myweb.jwt.CustomAuthenticationProvider;
import com.xbcai.myweb.jwt.JwtUserRepository;
import com.xbcai.myweb.model.JwtUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/jwt")
public class JwtController {
    @Autowired
    private JwtUserRepository jwtUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @PostMapping("/sign")
    public void sign(@RequestBody JwtUser jwtUser){
        jwtUser.setPassword(passwordEncoder.encode(jwtUser.getPassword()));
        jwtUserRepository.save(jwtUser);
    }
    @PostMapping(value = "/apiLogin")
    public void apiLogin(String username, String password, HttpServletResponse response) {
        System.out.println("--JwtController--login--username:"+username+",password:"+password);
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(username, password);
            Authentication result = customAuthenticationProvider.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            if (result != null) {
                // 自定义生成Token返给前端这里可以根据用户信息查询对应的角色信息
                Map<String,Object> map = new HashMap<String,Object>(2);
                map.put("name",result.getPrincipal());
                map.put("authorities",result.getAuthorities());
                String token = Jwts.builder()
                        .setSubject(result.getPrincipal().toString())
                        .setClaims(map)
                        .setExpiration(new Date(System.currentTimeMillis() + 30 * 1000)) // 设置过期时间 30秒(实际项目需要根据自己的情况修改)
                        .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                        .compact();
                // 登录成功后，返回token到header里面
                response.addHeader("Authorization", "Bearer " + token);
            }
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }

    }
    @GetMapping("/main")
    public String main(){
        return "main";
    }

}
