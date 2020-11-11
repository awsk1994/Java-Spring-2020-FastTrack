package org.wong.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestProfile {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("dev");
        ctx.register(JavaConfig.class);
        ctx.refresh();
        System.out.println(ctx.getBean(DataSource.class));
    }
}
