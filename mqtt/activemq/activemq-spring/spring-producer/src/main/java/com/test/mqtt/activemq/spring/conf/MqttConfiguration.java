package com.test.mqtt.activemq.spring.conf;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfiguration {

	@Autowired
	private MqttProperties mqttProperties;

	@Bean
    public Queue queue() {
        return new ActiveMQQueue(mqttProperties.getQueueName());
    }

}
