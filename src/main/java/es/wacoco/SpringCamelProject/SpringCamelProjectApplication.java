package es.wacoco.SpringCamelProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringCamelProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCamelProjectApplication.class, args);
	}

}
