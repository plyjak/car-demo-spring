package ply.cars.interceptors;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiCallInterceptor implements HandlerInterceptor{
    
    private static Map<String, Integer> apiCallsCounter = new HashMap<>();
    
    public boolean preHandle(
        HttpServletRequest request, 
        HttpServletResponse response, 
        Object handler
    ){
        var uri = request.getRequestURI();
        apiCallsCounter.merge(uri, 1, (prev, one) -> prev + one);
        return true;
    }

    public static Map<String, Integer> getApiCallsCounter(){
        return Collections.unmodifiableMap(apiCallsCounter);
    }
}