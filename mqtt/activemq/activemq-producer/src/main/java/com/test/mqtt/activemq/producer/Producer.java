package com.test.mqtt.activemq.producer;

import javax.annotation.Resource;

import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Producer {

	@Resource
	private MqttPahoMessageHandler mqtt;

	@RequestMapping("/send")
	public void send(String msg) {
		Message<String> message = MessageBuilder.withPayload(msg).build();
		mqtt.handleMessage(message);
		System.out.println("成功");
	}

}
