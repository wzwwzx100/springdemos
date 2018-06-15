package com.test.mqtt.activemq;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class MqttClient3Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MqttClient3Application.class).web(
		        WebApplicationType.SERVLET).run(args);
	}

}
