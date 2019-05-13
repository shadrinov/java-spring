package ru.ntechs.insworks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ru.ntechs.insworks.ami.AMI;

@SpringBootApplication
public class Application {
	@Autowired
	private AMI ami;

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	public AMI getAmi() {
		return ami;
	}
}
