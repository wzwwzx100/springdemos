package com.test.mqtt.emq;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class EmqClient1Application {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(EmqClient1Application.class).web(
		        WebApplicationType.SERVLET).run(args);
	}

}
