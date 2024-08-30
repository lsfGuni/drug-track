package com.example.drugtrack;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication

public class DrugTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrugTrackApplication.class, args);
	}


}

class MyCustomBanner implements Banner {
	@Override
	public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
		out.println("My Custom Banner Text");
	}
}