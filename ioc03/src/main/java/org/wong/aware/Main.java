package org.wong.aware;

import org.springframework.beans.factory.Aware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        AwareService bean = ctx.getBean(AwareService.class);
        bean.output();
    }
}
