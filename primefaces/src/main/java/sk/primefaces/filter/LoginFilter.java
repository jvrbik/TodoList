package sk.primefaces.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.primefaces.bean.UserManagerBean;

public class LoginFilter implements Filter {

	private static final String LOGIN_PAGE = "/login.xhtml";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		// managed bean name is exactly the session attribute name
		UserManagerBean userManagerBean = (UserManagerBean) httpServletRequest.getSession()
				.getAttribute("userManagerBean");
		if (userManagerBean != null) {
			if (userManagerBean.isLoggedIn()) {
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
			}
		} else {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
