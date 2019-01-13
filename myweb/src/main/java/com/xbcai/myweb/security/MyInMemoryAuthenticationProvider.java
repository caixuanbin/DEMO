package com.xbcai.myweb.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MyInMemoryAuthenticationProvider implements AuthenticationProvider {
    private final String adminName = "root";
    private final String adminPassword = "root";

    //根用户拥有全部的权限
    private final List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("CAN_SEARCH"),
            new SimpleGrantedAuthority("CAN_SEARCH"),
            new SimpleGrantedAuthority("CAN_EXPORT"),
            new SimpleGrantedAuthority("CAN_IMPORT"),
            new SimpleGrantedAuthority("CAN_BORROW"),
            new SimpleGrantedAuthority("CAN_RETURN"),
            new SimpleGrantedAuthority("CAN_REPAIR"),
            new SimpleGrantedAuthority("CAN_DISCARD"),
            new SimpleGrantedAuthority("CAN_EMPOWERMENT"),
            new SimpleGrantedAuthority("CAN_BREED"));

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("--MyInMemoryAuthenticationProvider--authenticate--"+authentication.getPrincipal()+","+authentication.getAuthorities());
        if(isMatch(authentication)){
            User user = new User(authentication.getName(),authentication.getCredentials().toString(),authorities);
            return new UsernamePasswordAuthenticationToken(user,authentication.getCredentials(),authorities);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    private boolean isMatch(Authentication authentication){
        if(authentication.getName().equals(adminName)&&authentication.getCredentials().equals(adminPassword))
            return true;
        else
            return false;
    }
}
