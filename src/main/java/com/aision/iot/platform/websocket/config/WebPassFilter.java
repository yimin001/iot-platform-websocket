package com.aision.iot.platform.websocket.config;

import com.aision.iot.platform.websocket.constant.SysConstants;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yim
 * @description 跨域设置
 * @date 2019/5/6
 */
@WebFilter(filterName = "requestFilter",urlPatterns = {"/*"})
public class WebPassFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE,x-requested-with,Authorization");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        String method = httpServletRequest.getMethod();
        if(SysConstants.REQUEST_METHOD_OPTIONS.equalsIgnoreCase(method)){
            servletResponse.getOutputStream().write("Success".getBytes("utf-8"));
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
