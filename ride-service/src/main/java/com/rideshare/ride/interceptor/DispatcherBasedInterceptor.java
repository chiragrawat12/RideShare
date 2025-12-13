package com.rideshare.ride.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Spring HandlerInterceptor that delegates to InterceptorDispatcher
 * Bridges Spring's interceptor interface with our dispatcher pattern
 */
public class DispatcherBasedInterceptor implements HandlerInterceptor {

    private final InterceptorDispatcher dispatcher;

    public DispatcherBasedInterceptor(InterceptorDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        return dispatcher.executePreHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
        dispatcher.executePostHandle(request, response, handler, null);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception exception) throws Exception {
        dispatcher.executeAfterCompletion(request, response, handler, exception);
    }
}