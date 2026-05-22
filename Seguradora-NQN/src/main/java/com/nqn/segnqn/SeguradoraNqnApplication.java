package com.nqn.segnqn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SeguradoraNqnApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeguradoraNqnApplication.class, args);
	}

}
