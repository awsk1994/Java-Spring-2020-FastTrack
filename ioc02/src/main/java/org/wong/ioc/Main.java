package org.wong.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        ShowCmd cmd = (ShowCmd) ctx.getBean("cmd");
        String s = cmd.showCmd();
        System.out.println("cmd = " + s);

        AnnotationConfigApplicationContext ctx2 = new AnnotationConfigApplicationContext(JavaConfig2.class);
        System.out.println("user = " + (User) ctx2.getBean("user2"));
    }
}
