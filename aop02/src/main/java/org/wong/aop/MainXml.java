package org.wong.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wong.aop.service.MyCalculator;
import org.wong.aop.service.MyCalculatorImp;

public class MainXml {
    public static void main(String[] args) {
        System.out.println("Test");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        MyCalculator calculator = ctx.getBean(MyCalculatorImp.class);
        calculator.add(3,4);
        calculator.min(3,4);
        calculator.divide(1,0);
    }
}
