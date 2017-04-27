/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.ativ2.authagain.web;

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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ricardo Job
 */
@WebFilter(filterName = "TwitterFilter", urlPatterns = {"/faces/*"})
public class TwitterFilter implements Filter {

    private static final boolean debug = true;

    private FilterConfig filterConfig = null;

    public TwitterFilter() {
    }

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("TwitterFilter:doFilter()");
        }
        
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        
        if (session == null) {
            HttpServletResponse resp = (HttpServletResponse) response;
            
            resp.sendRedirect(request.getServletContext().getContextPath());
        } else {
            chain.doFilter(request, response);
        }

    }

    
    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("TwitterFilter:Initializing filter");
            }
        }
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
