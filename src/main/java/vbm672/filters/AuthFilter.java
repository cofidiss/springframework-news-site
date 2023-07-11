package vbm672.filters;



import java.io.IOException;

import javax.servlet.Filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import vbm672proje.service.Service;



@Component

public class AuthFilter implements Filter {


    @Override
    public void destroy() {
        // ...
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	   HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
    	   HttpServletResponse httpServletResponse = ((HttpServletResponse) response);
    	   ServletContext context = ((HttpServletRequest) request).getSession().getServletContext();
    	   Service service = new Service(context);
    		Cookie cookie[] = httpServletRequest.getCookies();
    		Boolean isCookieLegit = service.IsCookieLegit(cookie);
     		String requestUri = httpServletRequest.getRequestURI();
    				
    	   if (requestUri.equals("/vbm672/CategoryAdmin")) {
    		   
    	
    			    if (!(service.IsCategoryAdmin(cookie) || service.IsSiteAdmin(cookie)) ) {
    			    	 request.getRequestDispatcher("").forward(request, response);
     		            return;
    		        } 
    		   
    	   }
    	   if (requestUri.equals("/vbm672/SiteAdmin") ) {
    		   
    		   if ( service.IsSiteAdmin(cookie)== false )  {
			    	 request.getRequestDispatcher("").forward(request, response);
		            return;
		        } 
    		   
    	   }
	  
	
    	   chain.doFilter(request, response);
    
  } 
    }