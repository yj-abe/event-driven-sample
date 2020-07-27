package jp.cloudace.sample.eventdriven.infrastructure.event;


import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import jp.cloudace.sample.eventdriven.domain.event.DomainEventPublisher;

@Singleton
@WebFilter(urlPatterns = "/*")
public class DomainEventResetFilter implements Filter {

    @Inject
    private DomainEventPublisher publisher;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        publisher.reset();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
