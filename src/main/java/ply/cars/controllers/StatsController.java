package ply.cars.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ply.cars.interceptors.ApiCallInterceptor;

@RestController
public class StatsController {
    @GetMapping("/api/stats")
    public Map<String, Integer> getStats(){
        return ApiCallInterceptor.getApiCallsCounter();
    }
}
