package org.wong.ioc;

import org.wong.ioc.model.User;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
//        // 正常的方法
//        User user = new User();
//        System.out.println("user = " + user);

        // Instantiate Container (which instantiates the User)
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get user instance.
        User u1 = (User)ctx.getBean("user");
        User u2 = ctx.getBean("user", User.class);
        User u3 = ctx.getBean(User.class);
        System.out.println("u1 = " + u1);
        System.out.println("u2 = " + u2);
        System.out.println("u3 = " + u3);

//        // Another way to do what ClassPathXmlApplicationContext does.
//        FileSystemXmlApplicationContext ftx = new FileSystemXmlApplicationContext(<ABSOLUTE PATH>)
    }
}
