package org.wong.ioc;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wong.ioc.service.UserService;

public class XMLTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Using XML 配置
        UserService userService = ctx.getBean(UserService.class);
        System.out.println(userService.getAllUsers());
    }
}
