package ru.popov.christmas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ru.popov.christmas.service.dao")
public class ChristmasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChristmasApplication.class, args);
	}

}
