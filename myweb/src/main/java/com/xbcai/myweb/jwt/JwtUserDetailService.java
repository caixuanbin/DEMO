package com.xbcai.myweb.jwt;

import com.xbcai.myweb.model.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JwtUserDetailService implements UserDetailsService {
    @Autowired
    private JwtUserRepository jwtUserRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("--JwtUserDetailService--loadUserByUsername--"+s);
        JwtUser u = jwtUserRepository.findByUsername(s);
        if(u==null) throw new UsernameNotFoundException(s);
        return new User(u.getUsername(),u.getPassword(), Collections.emptyList());
    }
}
