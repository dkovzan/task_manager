package com.kovzan.task_manager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, initParams = {@WebInitParam(name="encoding", value="utf-8")})
public class EncodingFilter implements Filter {
	
	private String code;
	
	@Override
	public void init(FilterConfig filterConfig) {
		code = filterConfig.getInitParameter("encoding");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String codeRequest = request.getCharacterEncoding();
		if (code != null && !code.equalsIgnoreCase(codeRequest)) {
			request.getCharacterEncoding();
			response.getCharacterEncoding();
			request.setCharacterEncoding(code);
			response.setCharacterEncoding(code);
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		code = null;
	}
}
