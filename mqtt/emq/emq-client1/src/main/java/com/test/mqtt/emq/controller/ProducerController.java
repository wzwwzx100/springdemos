package com.test.mqtt.emq.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.mqtt.emq.conf.MqttConfiguration.MyGateway;

@RestController
public class ProducerController {
	
	private static Logger logger = LogManager.getLogger(ProducerController.class);

	@Autowired
	private MyGateway gateway;
	
	@RequestMapping("/send")
	public void send(String topic, String msg) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String payload = "foo-" + df.format(new Date());
		String message = "topic: " + topic + ", payload: " + payload;
		System.out.println(message);
		logger.info(message);
		gateway.sendToMqtt(payload, topic);
	}
}
