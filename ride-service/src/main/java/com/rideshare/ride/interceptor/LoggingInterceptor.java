package com.rideshare.ride.interceptor;

import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptorCallback {

    private static final String START_TIME_ATTR = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTR, startTime);

        String queryString = request.getQueryString();
        if (queryString != null) {
            System.out.println("Query Parameters: " + queryString);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           Exception exception) throws Exception {
        System.out.println("Response Status: " + response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception exception) throws Exception {

        long startTime = (long) request.getAttribute(START_TIME_ATTR);
        long duration = System.currentTimeMillis() - startTime;

        System.out.println("Response: " + response.getStatus());
        System.out.println("Response Time: " + duration + "ms");

        if (exception != null) {
            System.out.println("Exception: " + exception.getClass().getSimpleName());
        }
    }

    @Override
    public String getInterceptorName() {
        return "LoggingInterceptor";
    }
}