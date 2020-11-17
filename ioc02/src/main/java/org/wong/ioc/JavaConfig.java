package org.wong.ioc;

import org.springframework.context.annotation.*;

@Configuration
public class JavaConfig {
    @Bean("cmd")        // same name to allow @Condition to check which one to use.
    @Conditional(WindowsCondition.class)
    ShowCmd winCmd(){
        return new WindowsShowCmd();
    }   

    @Bean("cmd")        // same name to allow @Condition to check which one to use.
    @Conditional(MacCondition.class)
    ShowCmd macCmd(){
        return new MacShowCmd();
    }

    @Bean
    @Profile("dev")
    @Scope("prototype")
    DataSource devOps() {
        DataSource ds = new DataSource();
        ds.setUrl("abc");
        ds.setUsername("do_uname");
        ds.setPassword("do_uname_pw");
        return ds;
    }

    @Bean
    @Profile("prod")
    DataSource prodOps() {
        DataSource ds = new DataSource();
        ds.setUrl("abc_prod");
        ds.setUsername("do_uname_prod");
        ds.setPassword("do_uname_pw_prod");
        return ds;
    }

}
