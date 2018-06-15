package com.test.mqtt.activemq.spring.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.mqtt")
public class MqttProperties {
	
	private String queueName;

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

}
