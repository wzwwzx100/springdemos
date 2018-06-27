package com.test.mqtt.mosquitto.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.mqtt.mosquitto.conf.MqttConfiguration.MyGateway;

@RestController
public class ProducerController {

	@Autowired
	private MyGateway gateway;
	
	@RequestMapping("/send")
	public void send(String topic, String msg) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String payload = "foo-" + df.format(new Date());
		System.out.println("topic: " + topic + ", payload: " + payload);
		gateway.sendToMqtt(payload, topic);
	}
}
