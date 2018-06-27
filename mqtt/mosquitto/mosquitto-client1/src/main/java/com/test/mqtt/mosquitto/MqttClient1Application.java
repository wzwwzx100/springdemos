package com.test.mqtt.mosquitto;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class MqttClient1Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MqttClient1Application.class).web(
		        WebApplicationType.SERVLET).run(args);
	}

}
