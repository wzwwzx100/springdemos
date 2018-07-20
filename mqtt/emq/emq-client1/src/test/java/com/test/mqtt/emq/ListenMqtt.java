package com.test.mqtt.emq;

import java.util.Properties;

import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.test.mqtt.emq.util.PushCallback;
import com.test.mqtt.emq.util.SslUtil;

public class ListenMqtt {

	public static final String HOST = "ssl://10.135.78.20:8888";

	public static final String TOPIC1 = "1.0/GW/ a20170605120000";

	public static final String clientid = "client11" + System.currentTimeMillis();

	private MqttClient client;

	private MqttConnectOptions options;

	private String userName = "admin"; // 非必须

	private String passWord = "password"; // 非必须

	private void start() {
		try {
			client = new MqttClient(HOST, clientid, new MemoryPersistence());
			options = new MqttConnectOptions();
			String path = "/opt/ssl/test/";
			
			
//			SSLSocketFactory factory = SslUtil.getSocketFactory(path + "ca.crt", path + "client.crt",
//			        path + "client.der", "changeit");
//			options.setSocketFactory(factory);
			
			
			Properties sslClientProps = new Properties();
			sslClientProps.setProperty("com.ibm.ssl.protocol", "TLSv1.2");
			String keyStore = path + "client.jks";
			sslClientProps.setProperty("com.ibm.ssl.keyStore", keyStore);
			sslClientProps.setProperty("com.ibm.ssl.keyStorePassword", "changeit");
			sslClientProps.setProperty("com.ibm.ssl.keyStoreType", "JKS");
			String trustStore = path + "client-trust.jks";
			sslClientProps.setProperty("com.ibm.ssl.trustStore", trustStore);
			sslClientProps.setProperty("com.ibm.ssl.trustStorePassword", "changeit");
			sslClientProps.setProperty("com.ibm.ssl.trustStoreType", "JKS");
			options.setSSLProperties(sslClientProps);
			
			
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
