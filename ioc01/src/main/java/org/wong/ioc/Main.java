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
        /*
        User u1 = (User)ctx.getBean("user");
        User u2 = ctx.getBean("user", User.class);
        User u3 = ctx.getBean(User.class);  // This will give error because need uniqueBeanId
        System.out.println("u1 = " + u1);
        System.out.println("u2 = " + u2);
        System.out.println("u3 = " + u3);
        */

        User user1 = ctx.getBean("user", User.class);
        System.out.println("user1 = " + user1);

        User user2 = ctx.getBean("user2", User.class);
        System.out.println("user2 = " + user2);

        User user3 = ctx.getBean("user3", User.class);
        System.out.println("user3 = " + user3);

        User user4 = ctx.getBean("user4", User.class);
        System.out.println("user4 = " + user4);

        User user5 = ctx.getBean("user5", User.class);
        System.out.println("user5 = " + user5);


//        // Another way to do what ClassPathXmlApplicationContext does.
//        FileSystemXmlApplicationContext ftx = new FileSystemXmlApplicationContext(<ABSOLUTE PATH>)
    }
}
