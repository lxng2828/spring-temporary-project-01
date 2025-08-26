package com.vtlong.my_spring_boot_project.controller;

import com.vtlong.my_spring_boot_project.annotation.LogExecution;
import com.vtlong.my_spring_boot_project.response.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/hello")
    @LogExecution(description = "Get hello message")
    public ApiResponse<String> hello() {
        return ApiResponse.success("Hello World");
    }
}
