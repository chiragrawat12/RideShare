package com.rideshare.ride.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor Pattern - Logging
 * Logs request details and response time
 * Helps with debugging and performance monitoring
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // Store request start time
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        // Log query parameters if present
        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            System.out.println("Query Parameters: " + queryString);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

        long startTime = (long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Response Time: " + duration + "ms");
        System.out.println("=".repeat(80) + "\n");

        // Log exceptions if any
        if (ex != null) {
            System.out.println("Exception occurred: " + ex.getMessage());
        }
    }
}