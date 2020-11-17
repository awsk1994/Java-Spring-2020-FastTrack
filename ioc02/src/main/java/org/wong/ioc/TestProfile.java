package org.wong.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestProfile {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("dev");  // Configured with @Scope("prototype")
        ctx.register(JavaConfig.class);
        ctx.refresh();
        DataSource d1 = ctx.getBean(DataSource.class);
        DataSource d2 = ctx.getBean(DataSource.class);
        System.out.println(d1 == d2);

        AnnotationConfigApplicationContext ctx2 = new AnnotationConfigApplicationContext();
        ctx2.getEnvironment().setActiveProfiles("prod");    // Configured w/o @Scope("prototype")
        ctx2.register(JavaConfig.class);
        ctx2.refresh();
        DataSource d3 = ctx2.getBean(DataSource.class);
        DataSource d4 = ctx2.getBean(DataSource.class);
        System.out.println(d3 == d4);
    }
}
