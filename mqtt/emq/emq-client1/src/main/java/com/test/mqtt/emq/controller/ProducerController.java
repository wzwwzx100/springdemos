package com.test.mqtt.emq.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.mqtt.emq.conf.MqttConfiguration.MyGateway;

@RestController
public class ProducerController {
	
	private static Logger logger = LogManager.getLogger(ProducerController.class);

	@Autowired
	private MyGateway gateway;
	
//	@Autowired
//	private MqttAsyncClient mqttAsyncClient;
	
	@RequestMapping("/send")
	public void send(String topic, String msg) throws MqttPersistenceException, MqttException {
		String message = "send topic: " + topic + ", payload: " + msg;
		System.out.println(message);
		logger.info(message);
		gateway.sendToMqtt(msg, topic);
//		mqttAsyncClient.publish(topic, new MqttMessage(msg.getBytes()));
	}
}
