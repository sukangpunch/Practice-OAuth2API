package me.foroauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ForOauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(ForOauth2Application.class, args);
	}

}
