package org.wong.ioc;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MacCondition implements Condition {
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata){
        String osName = conditionContext.getEnvironment().getProperty("os.name");
        return osName.toLowerCase().contains("mac");
    }
}
