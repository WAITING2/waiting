package com.xing.paper.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
@WebFilter(filterName="student",urlPatterns="/student/*")
public class StudentFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String auth = (String) request.getSession().getAttribute("auth");
		if(StringUtils.isNoneBlank(auth) && auth.equals("student")){
			filterChain.doFilter(servletRequest, servletResponse);
		}else{
			response.sendRedirect(request.getContextPath()+"/");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
