package com.team.infra_team2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InfraTeam2Application {

	public static void main(String[] args) {
		SpringApplication.run(InfraTeam2Application.class, args);
	}

}
