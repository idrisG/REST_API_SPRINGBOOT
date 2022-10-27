package com.example.userapp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.userapp.annotation.Log;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Aspect to log input and output on the console
 * 
 * @author idris
 *
 */
@Aspect
@Component
public class LogAspect {

    /**
     * Method that logs input,output and time of execution of methods
     * 
     * @param joinPoint join point for advice
     * @param log       annotation
     * @return
     * @throws Throwable e
     */
    @Around("@annotation(log)")
    public Object logMethod(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        Instant begin = Instant.now();
        String methodName = joinPoint.getSignature().getName();
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        Map<String, Object> parameters = obtainParameters(joinPoint);
        logger.info("{}.{}() started with parameters: {}", declaringTypeName, methodName, parameters);
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            Instant end = Instant.now();
            long timeElapse = Duration.between(begin, end).toMillis();
            logger.error("{}.{}() failed in {} ms with exception message: {}", declaringTypeName, methodName,
                    timeElapse, e.getMessage());
            throw e;
        }
        Instant end = Instant.now();
        long timeElapse = Duration.between(begin, end).toMillis();
        logger.info("{}.{}() finished in {} ms with return value: {}", declaringTypeName, methodName, timeElapse,
                proceed);
        return proceed;
    }

    /**
     * Map parameters of join point
     * 
     * @param joinPoint
     * @return
     */
    private Map<String, Object> obtainParameters(ProceedingJoinPoint joinPoint) {

        String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        Map<String, Object> parameters = IntStream.range(0, parameterNames.length).boxed()
                .collect(Collectors.toMap(i -> parameterNames[i], i -> parameterValues[i]));
        return parameters;
    }
}