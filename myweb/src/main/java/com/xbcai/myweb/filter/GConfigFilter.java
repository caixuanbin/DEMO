package com.xbcai.myweb.filter;

import javax.servlet.*;
import java.io.IOException;

public class GConfigFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("---GConfigFilter---doFilter--");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("---GConfigFilter---init--");
    }

    @Override
    public void destroy() {
        System.out.println("---GConfigFilter---destroy--");
    }
}
