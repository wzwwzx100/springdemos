package com.test.mqtt.mosquitto;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class MqttClient2Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MqttClient2Application.class).web(WebApplicationType.NONE).run(args);
	}

}
