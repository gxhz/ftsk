package com.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOnFilter implements Filter {

	FilterConfig fc;  
	
    public void destroy() {   
    }   
    
    public void doFilter(ServletRequest request, ServletResponse response,  FilterChain chain) throws IOException, ServletException {   
        HttpServletRequest hreq=(HttpServletRequest) request;   
        HttpServletResponse hres=(HttpServletResponse) response;   
        HttpSession session = hreq.getSession();  
        
        String url = hreq.getRequestURI();
        if(url.indexOf(".jsp") >= 0) {  //只过滤jsp页面
            if((session != null)&&(session.getAttribute("loginname") != null)) {
                chain.doFilter(request, response);
            } else {
                if (url.indexOf("login.jsp") == -1) {
                    url = hreq.getContextPath()+"/login.jsp";
                    hres.sendRedirect(url);  //进入登录界面
                } else {
                    chain.doFilter(request, response);
                }
            }
        } else {  //不是jsp页面的不过滤
            chain.doFilter(request, response);
        }
        
    }
  
    public void init(FilterConfig fc) throws ServletException {   
        this.fc=fc;   
    }   
	
}
