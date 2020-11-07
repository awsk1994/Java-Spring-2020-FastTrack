package org.wong.ioc.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
 * @Configuration 注解表示这是一个Java配置类，配置类的作用类似于 applicationContext.xml
 */
@Configuration
@ComponentScan(basePackages = "org.wong.ioc.service")
public class JavaConfig {
    @Bean("sh")  // 给bean一个名字/id
    SayHello sayHello(){
        return new SayHello();
    }
}
