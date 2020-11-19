package org.wong.aop.service;

import org.springframework.stereotype.Component;
import org.wong.aop.Action;

@Component
public class MyCalculatorImp implements MyCalculator{
//    @Action // 侵入型，不建议用
    public int add(int a, int b) {
        System.out.println("add(" + a + ", " + b + ")");
        return a-b;
    }

//    @Action
    public void min(int a, int b) {
        System.out.println("min | " + a + "-" + b + " = " + (a-b));
    }

//    @Action
    public int divide(int a, int b){
        System.out.println("divide | " + a + ", " + b);
        return a/b;
    }
}
