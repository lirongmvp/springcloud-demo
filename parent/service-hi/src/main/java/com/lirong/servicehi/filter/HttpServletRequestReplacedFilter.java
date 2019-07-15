package com.lirong.servicehi.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Title: HttpServletRequestReplacedFilter <br>
 * Description: HttpServletRequestReplacedFilter <br>
 * Date: 2019年07月08日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public class HttpServletRequestReplacedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        }
        if (null == requestWrapper) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

}
