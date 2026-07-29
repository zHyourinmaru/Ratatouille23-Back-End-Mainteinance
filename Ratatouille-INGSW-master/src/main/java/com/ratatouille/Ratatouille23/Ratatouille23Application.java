package com.ratatouille.Ratatouille23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.ratatouille"})
@SpringBootApplication
public class Ratatouille23Application {
	public static void main(String[] args) {
		SpringApplication.run(Ratatouille23Application.class, args);
	}
}
