package com.rideshare.ride.config;

import com.rideshare.ride.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC Configuration with Dispatcher-based Interceptor Chain
 */
@Component
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    private ValidationInterceptor validationInterceptor;

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Autowired
    private InterceptorDispatcher interceptorDispatcher;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        System.out.println("\n" + "=".repeat(80));
        System.out.println("INITIALIZING INTERCEPTOR DISPATCHER CHAIN");
        System.out.println("=".repeat(80));

        // Register interceptors with dispatcher in order
        interceptorDispatcher.registerInterceptor(authenticationInterceptor);
        interceptorDispatcher.registerInterceptor(validationInterceptor);
        interceptorDispatcher.registerInterceptor(loggingInterceptor);

        System.out.println("\n✓ Total Interceptors Registered: " + interceptorDispatcher.getInterceptorCount());
        System.out.println("✓ Execution Order:");
        System.out.println("  1. AuthenticationInterceptor (check user)");
        System.out.println("  2. ValidationInterceptor (check data)");
        System.out.println("  3. LoggingInterceptor (record timing)");
        System.out.println("=".repeat(80) + "\n");

        // Register all interceptors with Spring for actual HTTP handling
        registry.addInterceptor(new DispatcherBasedInterceptor(interceptorDispatcher))
                .addPathPatterns("/api/**");
    }
}