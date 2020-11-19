package org.wong.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.wong.aop.service.MyCalculator;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        MyCalculator calculator = ctx.getBean(MyCalculator.class);
        calculator.add(3,4);
        calculator.min(3,4);
        calculator.divide(1,0);
    }
}
