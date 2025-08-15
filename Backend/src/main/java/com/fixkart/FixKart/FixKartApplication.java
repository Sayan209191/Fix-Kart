package com.fixkart.FixKart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class})*/
public class FixKartApplication {

	public static void main(String[] args) {
		SpringApplication.run(FixKartApplication.class, args);
	}

}
