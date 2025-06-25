package com.grd.online.paper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.grd.online.paper")
public class PaperApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaperApplication.class, args);
	}

}
