package org.apache.aphrodite.mvc.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class AphroditeFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest ;
//        String contextType = req.getContentType() ;
        HttpSession session = req.getSession() ;
        if(session == null ){

        }

    }

    public void destroy() {

    }
}
