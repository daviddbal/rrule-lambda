package net.balsoftware.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Limit the number of requests to the servlet
 * 
 * @author David Bal
 *
 */
@WebFilter(filterName="limitFilter", urlPatterns = {"/*"})
public class LimitFilter implements Filter {
    private final static int REQUEST_LIMIT = 10;
    private int count;
    private Object lock = new Object();
    private final static int RECURRENCE_LIMIT = 1000;

    @Override
	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
//    	System.out.println("filter" + count);
        try {
            boolean ok;
            synchronized (lock) {
                ok = count++ < REQUEST_LIMIT;
            }
            if (ok) {
            	String maxRecurrencesString = request.getParameter("maxRecurrences");
            	if (maxRecurrencesString != null)
            	{
	            	int maxRecurrences = Integer.parseInt(maxRecurrencesString);
	            	if (maxRecurrences > RECURRENCE_LIMIT)
	            	{
	            		throw new ServletException("Too Many Recurrences (must be <" + RECURRENCE_LIMIT + ")");
	            	}
            	}
                // let the request through and process as usual
                chain.doFilter(request, response);
            } else {
            	throw new ServletException("Too Many Requests");
                // handle limit case, e.g. return status code 429 (Too Many Requests)
                // see http://tools.ietf.org/html/rfc6585#page-3
            }
        } finally {
            synchronized (lock) {
                count--;
            }           
        }
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}