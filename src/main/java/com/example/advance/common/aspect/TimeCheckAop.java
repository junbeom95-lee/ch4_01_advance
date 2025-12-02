package com.example.advance.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeCheckAop {

    //어떤 것을 : userService 하위의 메서드
    //언제 : 실행 전후로
    //어떻게 : executionTimeMethod를 실행

    @Around("execution(* com.example.advance.user.service.*.*(..))")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        log.info("4번 째 : Service 레이어 메서드 실행 전 AOP 로직 수행");

        Object result = joinPoint.proceed();    //메서드 실행

        long endTime = System.currentTimeMillis();

        log.info("6번 째 : Service 레이어 메서드 실행 후 AOP 로직 수행");

        log.info("[AOP] {} 실행됨 in {}ms" , joinPoint.getSignature() , endTime - startTime);

        return result;
    }

    //로그인 인증인가를 Filter
    //user email 수정하는 것 -> Interceptor 수행
    //userEmailUpdate 메서드는 AOP 수행이 됨

    //요청이 들어옴  -> JwtFilter 통과 -> OwnerCheckInterceptor -> UserController 접근 -> userService의 userEmailUpdate 호출
    //-> 타겟 대상의 service 레이어의 메서드 실행 전 -> TimeCheckAop 감지 후 실행 전 로직 수행
    //userEmailUpdate 실행이 끝남 -> TimeCheckAop 감지 후 실행 후 로직 수행
    //Controller에서 return 값 넘겨줌
    //JwtFilter 통과해서 PostMan으로 결과 전달

}
