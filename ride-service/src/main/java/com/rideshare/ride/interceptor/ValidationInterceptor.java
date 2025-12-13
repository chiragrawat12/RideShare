package com.rideshare.ride.interceptor;

import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

@Component
public class ValidationInterceptor implements HandlerInterceptorCallback {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String method = request.getMethod();
        String path = request.getRequestURI();

        if (path.endsWith("/undo")) {
            return true;
        }

        if (method.equals("POST") || method.equals("PUT")) {
            String contentType = request.getHeader("Content-Type");

            if (contentType == null || !contentType.contains("application/json")) {
                System.out.println("VALIDATION FAILED: Content-Type must be application/json");
                sendErrorResponse(response, HttpStatus.BAD_REQUEST, "Content-Type must be application/json");
                return false;
            }

            System.out.println("✓ Validation: Content-Type = " + contentType);
        }

        if (path.contains("/search")) {
            String source = request.getParameter("source");
            String destination = request.getParameter("destination");

            if (source == null || source.isEmpty() ||
                    destination == null || destination.isEmpty()) {

                System.out.println("VALIDATION FAILED: Search requires source and destination parameters");
                sendErrorResponse(response, HttpStatus.BAD_REQUEST, "source and destination required");
                return false;
            }

            System.out.println("✓ Validation: Search parameters valid (" + source + " → " + destination + ")");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           Exception exception) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception exception) throws Exception {
        if (exception != null) {
            System.out.println("Validation - Exception occurred: " + exception.getMessage());
        }
    }

    @Override
    public String getInterceptorName() {
        return "ValidationInterceptor";
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   HttpStatus status,
                                   String message) throws Exception {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\", \"status\": " + status.value() + "}");
    }
}