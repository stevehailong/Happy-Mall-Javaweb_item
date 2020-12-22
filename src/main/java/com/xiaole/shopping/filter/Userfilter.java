package com.xiaole.shopping.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class Userfilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        HttpSession session=request.getSession();
        if(session.getAttribute("user")==null){
            response.sendRedirect("/login");
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
