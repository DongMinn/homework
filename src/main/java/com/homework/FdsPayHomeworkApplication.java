package com.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@ComponentScan(basePackages={"com.homework" ,"com.homework.rule"})
public class FdsPayHomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(FdsPayHomeworkApplication.class, args);
	}
}
