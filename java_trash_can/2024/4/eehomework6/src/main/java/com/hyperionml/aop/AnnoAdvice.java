package com.hyperionml.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect// 切面类
public class AnnoAdvice {

    // 其他通知都类似就只写这三个

    @Before("@annotation(com.hyperionml.anno.Log)")
    public void before() {
        System.out.println("前置通知");
    }

    @Around("@annotation(com.hyperionml.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕通知-前");
        // 调用原始方法
        Object result = joinPoint.proceed();
        System.out.println("环绕通知-后");

        return result;
    }

    @After("@annotation(com.hyperionml.anno.Log)")
    public void after(){
        System.out.println("后置通知");
    }

}
