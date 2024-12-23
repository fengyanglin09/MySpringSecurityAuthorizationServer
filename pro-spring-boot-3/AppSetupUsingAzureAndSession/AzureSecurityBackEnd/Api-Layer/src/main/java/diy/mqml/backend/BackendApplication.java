package diy.mqml.backend;

import diy.mqml.customstarterlayer.annotations.EnableMyRetroAudit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableMyRetroAudit
@SpringBootApplication
@EnableAsync
//@ComponentScan(basePackages = {"diy.mqml"})
//@EnableJpaRepositories(basePackages = {"diy.mqml"})
//@EntityScan(basePackages = {"diy.mqml"})
public class BackendApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(BackendApplication.class);
		app.setAdditionalProfiles("starter","security", "data", "actuator"); // app.setAdditionalProfiles("dev", "test"); allows you to specify multiple profiles that will be active at the same time.
		app.run(args);
	}

}
