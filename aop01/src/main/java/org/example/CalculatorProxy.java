package org.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CalculatorProxy {
    public static Object getInstance(final MyCalculatorImp myCalculator){
        return Proxy.newProxyInstance(CalculatorProxy.class.getClassLoader(),
                myCalculator.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     *
                     * @param proxy 代理对象
                     * @param method 代理方法
                     * @param args 方法参数
                     * @return
                     * @throws Throwable
                     */
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("Method start.");
                        Object invoke = method.invoke(myCalculator, args);
                        System.out.println("Method end.");
                        return invoke;
                    }
                });
    }
}
