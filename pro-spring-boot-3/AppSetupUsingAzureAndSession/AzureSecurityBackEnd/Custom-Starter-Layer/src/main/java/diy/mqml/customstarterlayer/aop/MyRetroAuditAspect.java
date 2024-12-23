package diy.mqml.customstarterlayer.aop;


import diy.mqml.customstarterlayer.annotations.MyRetroAudit;
import diy.mqml.customstarterlayer.annotations.MyRetroAuditIntercept;
import diy.mqml.customstarterlayer.annotations.MyRetroAuditStorage;
import diy.mqml.customstarterlayer.configuration.MyRetroAuditProperties;
import diy.mqml.customstarterlayer.formats.MyRetroAuditFormatStrategy;
import diy.mqml.customstarterlayer.formats.MyRetroAuditFormatStrategyFactory;
import diy.mqml.customstarterlayer.model.MyRetroAuditEvent;
import diy.mqml.customstarterlayer.model.MyRetroAuditEventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

@Slf4j(topic = "MyRetroAudit")
@AllArgsConstructor
@Aspect
public class MyRetroAuditAspect {

    private MyRetroAuditEventRepository eventRepository;
    private MyRetroAuditProperties properties;
    private MyRetroAuditStorage storage;

    @Around("@annotation(audit)")
    public Object auditAround(ProceedingJoinPoint joinPoint, MyRetroAudit audit) throws Throwable {
        MyRetroAuditEvent myRetroEvent = new MyRetroAuditEvent();
        myRetroEvent.setMethod(joinPoint.getSignature().getName());
        myRetroEvent.setArgs(audit.showArgs() ? Arrays.toString(joinPoint.getArgs()) : null);
        myRetroEvent.setMessage(audit.message());

        if (audit.intercept() == MyRetroAuditIntercept.BEFORE) {
            myRetroEvent.setInterceptor(MyRetroAuditIntercept.BEFORE.name());
        } else if (audit.intercept() == MyRetroAuditIntercept.AROUND) {
            myRetroEvent.setInterceptor(MyRetroAuditIntercept.AROUND.name());
        }

        Object result = joinPoint.proceed(joinPoint.getArgs());
        myRetroEvent.setResult("customer starter aop logging worked");
//        myRetroEvent.setResult(result.toString());

        if (audit.intercept() == MyRetroAuditIntercept.AFTER) {
            myRetroEvent.setInterceptor(MyRetroAuditIntercept.AFTER.name());
        }

        // Database, Console or File
        if (storage == MyRetroAuditStorage.DATABASE) {
            myRetroEvent = eventRepository.save(myRetroEvent);
        }

        // Logger or Console
        String formattedEvent = formatEvent(audit, myRetroEvent);
        if (properties.getUseLogger()) {
            log.info("{}{}", properties.getPrefix(), formattedEvent);
        } else {
            System.out.println(properties.getPrefix() + formattedEvent);
        }

        return result;
    }

    private String formatEvent(MyRetroAudit audit, MyRetroAuditEvent myRetroEvent) {
        MyRetroAuditFormatStrategy strategy = MyRetroAuditFormatStrategyFactory.getStrategy(audit.format());
        return audit.prettyPrint() ? strategy.prettyFormat(myRetroEvent) : strategy.format(myRetroEvent);
    }
}


