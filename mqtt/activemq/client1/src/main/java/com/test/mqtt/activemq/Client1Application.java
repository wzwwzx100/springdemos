package com.test.mqtt.activemq;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Client1Application {

	public static void main(String[] args) {
		 new SpringApplicationBuilder(Client1Application.class)
         .web(WebApplicationType.NONE)
         .run(args);
	}

}
