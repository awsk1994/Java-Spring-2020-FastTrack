package org.example;

public class Main {
    public static void main(String[] args) {
        MyCalculatorImp myCalculatorImp = new MyCalculatorImp();
        MyCalculator myCalculator = (MyCalculator) CalculatorProxy.getInstance(myCalculatorImp);
        int add = myCalculator.add(1,2);
        System.out.println("add = " + add);
    }
}
