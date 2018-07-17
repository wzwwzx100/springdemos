package com.test.mqtt.emq.util;

import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ListenMqtt {

	public static final String HOST = "ssl://10.135.78.20:8888";

	public static final String TOPIC1 = "1.0/aaa/GW";

	public static final String clientid = "client11";

	private MqttClient client;

	private MqttConnectOptions options;

	private String userName = "admin"; // 非必须

	private String passWord = "password"; // 非必须

	private void start() {
		try {
			client = new MqttClient(HOST, clientid, new MemoryPersistence());
			options = new MqttConnectOptions();
			SSLSocketFactory factory = SslUtil.getSocketFactory("d:/opt/ssl/ca/ca.crt", "d:/opt/ssl/23/client.crt",
			        "d:/opt/ssl/23/client.der", "changeit");
			
			options.setSocketFactory(factory);
			
			options.setCleanSession(false);
			options.setUserName(userName);
			options.setPassword(passWord.toCharArray());
			options.setConnectionTimeout(10);
			options.setKeepAliveInterval(20);
			client.setCallback(new PushCallback());
			MqttTopic topic = client.getTopic(TOPIC1);
			options.setWill(topic, "close".getBytes(), 2, true);
			client.connect(options);
			int[] Qos = { 1 };
			String[] topic1 = { TOPIC1 };
			client.subscribe(topic1, Qos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ListenMqtt client = new ListenMqtt();
		client.start();
	}

}
