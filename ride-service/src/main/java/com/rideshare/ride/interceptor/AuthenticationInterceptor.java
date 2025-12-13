package com.rideshare.ride.interceptor;

import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class AuthenticationInterceptor implements HandlerInterceptorCallback {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        System.out.println("\n" + "=".repeat(72));
        System.out.println("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " +
                request.getMethod() + " " + request.getRequestURI());
        System.out.println("=".repeat(72));

        String userIdHeader = request.getHeader("X-User-Id");

        if (userIdHeader == null || userIdHeader.isEmpty()) {
            System.out.println("REJECTED: Missing or empty X-User-Id header");
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Missing X-User-Id header");
            return false;
        }

        try {
            Long userId = Long.parseLong(userIdHeader);
            System.out.println("✓ Authentication passed - User ID: " + userId);
            request.setAttribute("userId", userId);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("REJECTED: X-User-Id is not a number");
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "X-User-Id must be a number");
            return false;
        }
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
            System.out.println("Authentication - Exception occurred: " + exception.getMessage());
        }
    }

    @Override
    public String getInterceptorName() {
        return "AuthenticationInterceptor";
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   HttpStatus status,
                                   String message) throws Exception {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\", \"status\": " + status.value() + "}");
    }
}