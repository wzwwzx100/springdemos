package com.test.mqtt.activemq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.mqtt.activemq.model.PushPayload;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Client1Application.class)
@WebAppConfiguration
public class Client1Test {

	@Autowired
	private MqttClientComponent mqttClientComponent;

	@Test
	public void test() {
		PushPayload pushPayload = PushPayload.getPushPayloadBuider().setContent("test").setMobile("119")
		        .setType("2018").bulid();
		mqttClientComponent.push("topic1", pushPayload);
	}

}
