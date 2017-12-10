package org.rambo.spring.boot.weixin.config;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.rambo.spring.boot.weixin.filter.TestFilter;
import org.rambo.spring.boot.weixin.listener.DemoListener;
import org.rambo.spring.boot.weixin.servlet.DemoServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import weixin.popular.example.TokenManagerListener;

/**
 * 
 * Add servlet, filter & listener into springboot Ref.
 * http://www.tianshouzhi.com/api/tutorials/springboot/89
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * 
	 * @function:  config the filter list
	 * @return
	 * @author: Rambo Zhu     12 Oct 2017 7:24:15 pm
	 */
	//	@Bean
	public FilterRegistrationBean getFilterList() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new TestFilter());
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");// 拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setName("testFilter");
		registrationBean.setOrder(1);
		return registrationBean;
	}

	/**
	 * 
	 * @function: config the servlet list
	 * @return
	 * @author: Rambo Zhu     12 Oct 2017 7:25:02 pm
	 */
//	@Bean
	public ServletRegistrationBean getServletList() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean();
		registrationBean.setServlet(new DemoServlet());
		List<String> urlMappings = new ArrayList<String>();
		urlMappings.add("/demoservlet");//// 访问，可以添加多个
		registrationBean.setUrlMappings(urlMappings);
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}

	@Bean
	public ServletListenerRegistrationBean<EventListener> getListenerList() {
		ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
		registrationBean.setListener(new TokenManagerListener());
		// registrationBean.setListener(new DemoListener());
		 registrationBean.setOrder(1);
		return registrationBean;
	}
	
}