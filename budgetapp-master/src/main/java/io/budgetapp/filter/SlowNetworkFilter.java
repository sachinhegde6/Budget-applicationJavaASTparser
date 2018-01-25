package io.budgetapp.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class SlowNetworkFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1L));
			ASTClass.instrum("Expression Statement","25");
        } catch (InterruptedException e) {
            e.printStackTrace();
			ASTClass.instrum("Expression Statement","27");
        } finally {
            filterChain.doFilter(servletRequest, servletResponse);
			ASTClass.instrum("Expression Statement","29");
        }
    }

    @Override
    public void destroy() {

    }
}
