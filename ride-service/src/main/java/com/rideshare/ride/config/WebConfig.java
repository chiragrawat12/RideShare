package com.rideshare.ride.config;

import com.rideshare.ride.interceptor.AuthenticationInterceptor;
import com.rideshare.ride.interceptor.LoggingInterceptor;
import com.rideshare.ride.interceptor.ValidationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configuration
 * Registers all interceptors in the correct order
 * Order: Authentication → Validation → Logging
 */
@Component
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    private ValidationInterceptor validationInterceptor;

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("🔧 Registering Interceptors...");

        // 1. Authentication Interceptor (first - checks user exists)
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/v1/health");
        System.out.println("✓ AuthenticationInterceptor registered");

        // 2. Validation Interceptor (second - checks data format)
        registry.addInterceptor(validationInterceptor)
                .addPathPatterns("/api/**");
        System.out.println("✓ ValidationInterceptor registered");

        // 3. Logging Interceptor (third - records request/response)
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/api/**");
        System.out.println("✓ LoggingInterceptor registered");

        System.out.println("✅ All interceptors configured successfully!\n");
    }
}