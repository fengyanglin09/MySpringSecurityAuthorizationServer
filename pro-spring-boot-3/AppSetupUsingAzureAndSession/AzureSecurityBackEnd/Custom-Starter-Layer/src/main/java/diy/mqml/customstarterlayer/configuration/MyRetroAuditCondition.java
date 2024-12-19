package diy.mqml.customstarterlayer.configuration;


import diy.mqml.customstarterlayer.annotations.EnableMyRetroAudit;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

public class MyRetroAuditCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !Objects.requireNonNull(context.getBeanFactory()).getBeansWithAnnotation(EnableMyRetroAudit.class).isEmpty();
    }
}
