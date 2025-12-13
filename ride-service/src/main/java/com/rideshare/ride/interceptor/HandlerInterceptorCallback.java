package com.rideshare.ride.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface for interceptor callback functions
 * Defines contract for proper interceptor implementation
 */
public interface HandlerInterceptorCallback {

    /**
     * Pre-handle callback - executed before controller
     * @return true to continue chain, false to stop
     */
    boolean preHandle(HttpServletRequest request,
                      HttpServletResponse response,
                      Object handler) throws Exception;

    /**
     * Post-handle callback - executed after controller (before view rendering)
     */
    void postHandle(HttpServletRequest request,
                    HttpServletResponse response,
                    Object handler,
                    Exception exception) throws Exception;

    /**
     * Completion callback - executed after response sent
     */
    void afterCompletion(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler,
                         Exception exception) throws Exception;

    /**
     * Get interceptor name for logging
     */
    String getInterceptorName();
}