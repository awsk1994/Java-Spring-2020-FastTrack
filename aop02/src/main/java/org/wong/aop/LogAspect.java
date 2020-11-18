package org.wong.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
// 表示这是一个切面
public class LogAspect {
    /**
     * 前置通知；在method逻辑／代码之前执行
     * @param joinPoint
     */
    @Before("@annotation(Action)")  // To indicate to use this before the @Action
    public void before(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法开始执行了。(@Before)");
    }

    /**
     * 后知通知；在方法逻辑／代码之后执行
     * @param joinPoint
     */
    @After("@annotation(Action)") //
    public void after(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法结束了。(@After)");
    }

    /**
     * @@AfterReturning 表示这是一个返回通知，即有目标方法有返回的时候才会触发。
     * 该注解中的returning属性表示目标方法返回值的变量名，这个需要和参数一一对应。
     * 注意：目标方法的返回值类型要和这里方法返回值参数的类型一致，否侧拦截不到
     * 如果拦截所有（包括回返值为void），则方法返回值参数可以为Object。
     * @param joinPoint
     * @param r
     */
    @AfterReturning(value = "@annotation(Action)", returning = "r")
    public void afterReturning(JoinPoint joinPoint, Integer r){
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法返回:" + r + "(@AfterReturning)");
    }

    /**
     * 异常通知，当目标方法抛出异常时，该方法会被触发
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "@annotation(Action)", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e){
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法异常通知:" + e.getMessage());
    }

    /**
     * 环绕通知
     * 是集大成者，可以用环绕通知实现上面的四个通知，这个方法的核心有点类似于在这里通过反射执行方法
     * @param pjp
     * @return
     */
    @Around("@annotation(Action)")
    public Object around(ProceedingJoinPoint pjp){
        Object proceed = null;
        try{
            proceed = pjp.proceed();
        } catch(Throwable throwable){
            throwable.printStackTrace();
        }
        return proceed;
    };
}
