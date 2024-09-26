package com.example.drugtrack;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class DrugTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrugTrackApplication.class, args);
	}


}
