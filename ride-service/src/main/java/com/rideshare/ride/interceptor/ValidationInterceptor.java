package com.rideshare.ride.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor Pattern - Validation
 * Validates request data before reaching controller
 * Checks: Content-Type, required parameters
 */
@Component
public class ValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String method = request.getMethod();
        String path = request.getRequestURI();

        // Validate Content-Type for POST/PUT requests
        if (method.equals("POST") || method.equals("PUT")) {
            String contentType = request.getContentType();

            if (contentType == null || !contentType.contains("application/json")) {
                System.out.println("REJECTED: Invalid Content-Type: " + contentType);
                System.out.println("   Expected: application/json");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Content-Type must be application/json\", \"status\": 400}");
                return false;
            }

            System.out.println("✓ Validation: Content-Type = application/json");
        }

        // Validate search parameters
        if (path.contains("/search")) {
            String source = request.getParameter("source");
            String destination = request.getParameter("destination");

            if (source == null || source.trim().isEmpty() ||
                    destination == null || destination.trim().isEmpty()) {

                System.out.println("REJECTED: Missing required parameters for /search");
                System.out.println("   Required: source, destination");
                System.out.println("   Provided: source=" + source + ", destination=" + destination);

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Missing required parameters: source and destination\", \"status\": 400}");
                return false;
            }

            System.out.println("✓ Validation: Search parameters valid (" + source + " → " + destination + ")");
        }

        return true;
    }
}