package com.cos.aopdemo.config;

import com.cos.aopdemo.domain.CommonDto;
import io.sentry.Sentry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

// @Controller, @RestController, @Component, @Configuration
// Controller 이후에 메모리 로드
@Component
@Aspect
public class BindingAdvice {

    private static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);

    // 1. 제어 Case
    // 함수 : 앞 뒤
    // 함수 : 앞 (username 이 안들어왔으면 내가 강제로 넣어주고 실행)
    // 함수 : 뒤 (응답만 관리)

    // ProceedingJoinPoint: 핵심기능
    // @Before
    // @After
    @Around("execution(* com.cos.aopdemo.web..*Controller.*(..))")
    public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();

        System.out.println("type: " + type);
        System.out.println("method: " + method);

        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg: args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error: bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                        // Debug mode
                        // Log level error, warn, info, debug
                        log.warn(type + "." + method + "() => 필드: " + error.getField() +
                                ", 메세지: " + error.getDefaultMessage());

                        log.debug(type + "." + method + "() => 필드: " + error.getField() +
                                ", 메세지: " + error.getDefaultMessage());

                        Sentry.captureMessage(type + "." + method + "() => 필드: " + error.getField() +
                                ", 메세지: " + error.getDefaultMessage());

                        // DB 연결 -> DB 남기기
                        // File file = new File(); // 비추천
                    }

                    return new CommonDto<Map>(HttpStatus.BAD_REQUEST.value(), errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }

    // Joinpoint 이전 실행 후 무조건 stack 실행하기 때문에 ProceedingJoinPoint 메타 정보가 필요 없음.
    @Before("execution(* com.cos.aopdemo.web..*Controller.*(..))")
    public void beforeCheck() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
                .getRequest();
        System.out.println("주소: " + request.getRequestURL());

        // Request 값 처리 못하나요?
        // log 처리는? 파일로 남기죠?
        System.out.println("AOP Before 처리");
    }

    // Joinpoint 이전 실행 후 -> 무조건 stack 실행하기 때문에 ProceedingJoinPoint 메타 정보가 필요 없음.
    @After("execution(* com.cos.aopdemo.web..*Controller.*(..))")
    public void afterCheck() {
        // Request 값 처리 못하나요?
        // log 처리는? 파일로 남기죠?
        System.out.println("AOP After 처리");
    }
}
