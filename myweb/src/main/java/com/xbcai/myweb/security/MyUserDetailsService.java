package com.xbcai.myweb.security;

import com.xbcai.myweb.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private SecurityUserRepository securityUserRepository;
        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            SecurityUser user = securityUserRepository.findByUsername(s);
            System.out.println("--MyUserDetailsService--loadUserByUsername:"+s+
                    ",password:"+passwordEncoder.encode(user.getPassword())+",authorities="+user.getAuthorities());
            //passwordEncoder每次编码出来的密码都是不一样的类似：$2a$10$mvz3cES57h5t/kl2WIfbce5eM.r4Edsy6lsuJMgnOMuQNDMNL.n6m
            return  new User(user.getUsername(),passwordEncoder.encode(user.getPassword()), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities()));
        }
}
