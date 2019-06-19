package ru.ntechs.insworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"ru.ntechs.ami"})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
    }
}
