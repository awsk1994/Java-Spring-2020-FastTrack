package org.wong.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
// 表示这是一个切面
public class LogAspect {
    /**
     * 前置通知；在method逻辑／代码之前的
     * @param joinPoint
     */
    @Before("@annotation(Action)")  // To indicate to use this before the @Action
    public void before(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法开始执行了。");
    }
}
