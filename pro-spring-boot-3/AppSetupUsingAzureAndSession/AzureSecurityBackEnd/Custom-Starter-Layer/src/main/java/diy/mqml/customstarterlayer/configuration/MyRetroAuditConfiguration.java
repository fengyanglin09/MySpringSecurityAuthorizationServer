package diy.mqml.customstarterlayer.configuration;


import diy.mqml.customstarterlayer.annotations.EnableMyRetroAuditValueProvider;
import diy.mqml.customstarterlayer.aop.MyRetroAuditAspect;
import diy.mqml.customstarterlayer.listener.MyRetroAuditListener;
import diy.mqml.customstarterlayer.model.MyRetroAuditEventRepository;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableConfigurationProperties(MyRetroAuditProperties.class)
@Conditional(MyRetroAuditCondition.class)
@EnableJpaRepositories(basePackages = "diy.mqml")
@ComponentScan(basePackages = "diy.mqml")
@EntityScan(basePackages = "diy.mqml")
@AutoConfiguration
public class MyRetroAuditConfiguration {

    @Bean
    public MyRetroAuditListener myRetroListener() {
        return new MyRetroAuditListener();
    }

    @Bean
    public MyRetroAuditAspect myRetroAuditAspect(MyRetroAuditEventRepository myRetroAuditEventRepository, MyRetroAuditProperties properties) {
        return new MyRetroAuditAspect(myRetroAuditEventRepository, properties, EnableMyRetroAuditValueProvider.getStorage());
    }

}
