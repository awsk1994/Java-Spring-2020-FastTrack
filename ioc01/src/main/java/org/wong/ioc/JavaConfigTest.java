package org.wong.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.wong.ioc.javaconfig.JavaConfig;
import org.wong.ioc.javaconfig.SayHello;

public class JavaConfigTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        SayHello sayHello = ctx.getBean("sh", SayHello.class); // 这个是根据 JavaConfig class里的方法名字.
        System.out.println(sayHello.sayHello("Wong"));
    }
}
