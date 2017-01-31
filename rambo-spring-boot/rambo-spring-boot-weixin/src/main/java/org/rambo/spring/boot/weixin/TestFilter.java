package org.rambo.spring.boot.weixin;


import javax.servlet.*;
import java.io.IOException;
/**
 * self defined filter in springboot
 */
public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init TestFilter");

    }

    @Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("In TestFilter");
		chain.doFilter(request, response);
		
	}
    @Override
    public void destroy() {
        System.out.println("destroy TestFilter");

    }
}