package org.wong.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class MyCalculatorImp implements MyCalculator{
    @Action // 侵入型，不建议用
    public int add(int a, int b) {
        System.out.println("add(" + a + ", " + b + ")");
        return a-b;
    }

    public void min(int a, int b) {
        System.out.println("min | " + a + "-" + b + " = " + (a-b));
    }
}
