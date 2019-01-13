package com.xbcai.myweb.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    /**
     * 如果AuthenticationProvider返回了null，
     * AuthenticationManager会交给下一个支持authentication类型的AuthenticationProvider处理。
     */
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        System.out.println("--MyAuthenticationProvider--authenticate--"+auth.getPrincipal());
        return null;
    }

    /**
     * support方法检查authentication的类型是不是这个AuthenticationProvider支持的，
     * 这里我简单地返回true，就是所有都支持
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
