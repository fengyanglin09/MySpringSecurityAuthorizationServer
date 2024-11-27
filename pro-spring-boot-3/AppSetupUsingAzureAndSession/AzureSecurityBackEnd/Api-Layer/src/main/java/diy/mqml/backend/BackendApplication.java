package diy.mqml.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"diy.mqml"})
public class BackendApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(BackendApplication.class);
		app.setAdditionalProfiles("security", "data"); // app.setAdditionalProfiles("dev", "test"); allows you to specify multiple profiles.
		app.run(args);
	}

}
