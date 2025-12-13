package com.rideshare.ride.interceptor;

import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Dispatcher for managing interceptor chain execution
 * Handles pre-processing, post-processing, and completion callbacks
 */
@Component
public class InterceptorDispatcher {

    private final List<HandlerInterceptorCallback> interceptors = new ArrayList<>();

    /**
     * Register interceptor with dispatcher
     */
    public void registerInterceptor(HandlerInterceptorCallback interceptor) {
        interceptors.add(interceptor);
        System.out.println("✓ Registered interceptor: " + interceptor.getInterceptorName());
    }

    /**
     * Execute pre-handle phase for all interceptors
     */
    public boolean executePreHandle(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object handler) throws Exception {

        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERCEPTOR CHAIN - PRE-PROCESSING PHASE");
        System.out.println("=".repeat(80));

        for (HandlerInterceptorCallback interceptor : interceptors) {
            System.out.println("\n→ Executing: " + interceptor.getInterceptorName());

            boolean shouldContinue = interceptor.preHandle(request, response, handler);

            if (!shouldContinue) {
                System.out.println("CHAIN STOPPED at: " + interceptor.getInterceptorName());
                System.out.println("=".repeat(80) + "\n");
                return false;
            }

            System.out.println("✓ " + interceptor.getInterceptorName() + " passed");
        }

        System.out.println("\n✓ ALL PRE-HANDLERS PASSED - PROCEEDING TO CONTROLLER");
        System.out.println("=".repeat(80) + "\n");
        return true;
    }

    /**
     * Execute post-handle phase for all interceptors (in reverse order)
     */
    public void executePostHandle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object handler,
                                  Exception exception) throws Exception {

        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERCEPTOR CHAIN - POST-PROCESSING PHASE");
        System.out.println("=".repeat(80));

        for (int i = interceptors.size() - 1; i >= 0; i--) {
            HandlerInterceptorCallback interceptor = interceptors.get(i);
            System.out.println("\n→ Post-handling: " + interceptor.getInterceptorName());

            interceptor.postHandle(request, response, handler, exception);

            System.out.println("✓ " + interceptor.getInterceptorName() + " completed");
        }

        System.out.println("\n✓ ALL POST-HANDLERS COMPLETED");
        System.out.println("=".repeat(80) + "\n");
    }

    /**
     * Execute completion callbacks for all interceptors (in reverse order)
     */
    public void executeAfterCompletion(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Object handler,
                                       Exception exception) throws Exception {

        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERCEPTOR CHAIN - COMPLETION PHASE");
        System.out.println("=".repeat(80));

        for (int i = interceptors.size() - 1; i >= 0; i--) {
            HandlerInterceptorCallback interceptor = interceptors.get(i);
            System.out.println("\n→ Completion callback: " + interceptor.getInterceptorName());

            interceptor.afterCompletion(request, response, handler, exception);

            System.out.println("✓ " + interceptor.getInterceptorName() + " completion done");
        }

        System.out.println("\n✓ ALL COMPLETION CALLBACKS EXECUTED");
        System.out.println("=".repeat(80) + "\n");
    }

    public List<HandlerInterceptorCallback> getInterceptors() {
        return new ArrayList<>(interceptors);
    }

    public int getInterceptorCount() {
        return interceptors.size();
    }
}