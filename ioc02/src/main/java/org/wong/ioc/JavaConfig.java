package org.wong.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

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
}
