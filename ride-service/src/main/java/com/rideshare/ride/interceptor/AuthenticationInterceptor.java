package com.rideshare.ride.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String USER_ID_HEADER = "X-User-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestPath = request.getRequestURI();
        String method = request.getMethod();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Log incoming request
        System.out.println("\n" + "=".repeat(80));
        System.out.println("[" + timestamp + "] " + method + " " + requestPath);
        System.out.println("=".repeat(80));

        // Skip authentication for health checks
        if (requestPath.contains("/health")) {
            System.out.println("ℹHealth check - skipping authentication");
            return true;
        }

        // Check X-User-Id header (required for all ride operations)
        String userId = request.getHeader(USER_ID_HEADER);

        if (userId == null || userId.trim().isEmpty()) {
            System.out.println("REJECTED: Missing or empty X-User-Id header");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Missing X-User-Id header\", \"status\": 401}");
            return false;
        }

        // Validate userId is numeric
        try {
            Long.parseLong(userId);
        } catch (NumberFormatException e) {
            System.out.println("REJECTED: X-User-Id is not numeric: " + userId);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"X-User-Id must be numeric\", \"status\": 400}");
            return false;
        }

        // Authentication successful
        System.out.println("✓ Authentication passed - User ID: " + userId);

        // Store userId in request for later use
        request.setAttribute("userId", userId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

        int statusCode = response.getStatus();
        String statusEmoji = statusCode >= 400 ? "x" : "✓";
        String statusText = statusCode >= 200 && statusCode < 300 ? "Success" :
                statusCode >= 300 && statusCode < 400 ? "Redirect" :
                        statusCode >= 400 && statusCode < 500 ? "Client Error" : "Server Error";

        System.out.println(statusEmoji + " Response: " + statusCode + " (" + statusText + ")");
    }
}