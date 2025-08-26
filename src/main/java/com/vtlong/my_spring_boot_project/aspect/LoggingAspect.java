package com.vtlong.my_spring_boot_project.aspect;

import com.vtlong.my_spring_boot_project.annotation.LogExecution;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Log trước khi thực thi method trong controller
    @Before("execution(* com.vtlong.my_spring_boot_project.controller.*.*(..))")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        log.info("=== CONTROLLER CALL ===");
        log.info("Class: {}", className);
        log.info("Method: {}", methodName);
        log.info("Parameters: {}", Arrays.toString(args));
        log.info("=======================");
    }

    // Log trước và sau khi thực thi method có annotation @LogExecution
    @Around("@annotation(com.vtlong.my_spring_boot_project.annotation.LogExecution)")
    public Object logAroundAnnotatedMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogExecution logExecution = method.getAnnotation(LogExecution.class);
        
        String methodName = method.getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String description = logExecution.description();
        Object[] args = joinPoint.getArgs();
        
        long startTime = System.currentTimeMillis();
        
        log.info("=== ANNOTATED METHOD START ===");
        log.info("Description: {}", description.isEmpty() ? "No description" : description);
        log.info("Class: {}", className);
        log.info("Method: {}", methodName);
        log.info("Parameters: {}", Arrays.toString(args));
        
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            log.info("=== ANNOTATED METHOD SUCCESS ===");
            log.info("Description: {}", description.isEmpty() ? "No description" : description);
            log.info("Class: {}", className);
            log.info("Method: {}", methodName);
            log.info("Execution time: {} ms", executionTime);
            log.info("Result: {}", result);
            log.info("===============================");
            
            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            log.error("=== ANNOTATED METHOD ERROR ===");
            log.error("Description: {}", description.isEmpty() ? "No description" : description);
            log.error("Class: {}", className);
            log.error("Method: {}", methodName);
            log.error("Execution time: {} ms", executionTime);
            log.error("Error: {}", e.getMessage());
            log.error("=============================");
            
            throw e;
        }
    }

    // Log trước và sau khi thực thi method trong service
    @Around("execution(* com.vtlong.my_spring_boot_project.service.*.*(..))")
    public Object logAroundServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        long startTime = System.currentTimeMillis();
        
        log.info("=== SERVICE METHOD START ===");
        log.info("Class: {}", className);
        log.info("Method: {}", methodName);
        log.info("Parameters: {}", Arrays.toString(args));
        
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            log.info("=== SERVICE METHOD SUCCESS ===");
            log.info("Class: {}", className);
            log.info("Method: {}", methodName);
            log.info("Execution time: {} ms", executionTime);
            log.info("Result: {}", result);
            log.info("=============================");
            
            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            log.error("=== SERVICE METHOD ERROR ===");
            log.error("Class: {}", className);
            log.error("Method: {}", methodName);
            log.error("Execution time: {} ms", executionTime);
            log.error("Error: {}", e.getMessage());
            log.error("===========================");
            
            throw e;
        }
    }

    // Log khi có exception xảy ra
    @AfterThrowing(
        pointcut = "execution(* com.vtlong.my_spring_boot_project.*.*(..))",
        throwing = "ex"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        log.error("=== EXCEPTION OCCURRED ===");
        log.error("Class: {}", className);
        log.error("Method: {}", methodName);
        log.error("Exception: {}", ex.getClass().getSimpleName());
        log.error("Message: {}", ex.getMessage());
        log.error("Stack trace:", ex);
        log.error("=========================");
    }
}
