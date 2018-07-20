package com.test.mqtt.emq;

import java.util.Properties;

import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.test.mqtt.emq.util.SslUtil;

public class PahoTlsExample {

	public static void main(String[] args) {

		String topic = "1.0/aaa/GW";

		int qos = 2;
		String broker = "ssl://10.135.78.20:8888";
		String clientId = "JavaSample" + System.currentTimeMillis();
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(true);
			String path = "/opt/ssl/test/";
			
//			SSLSocketFactory factory = null;
//			try {
//				factory = SslUtil.getSSLSocktet(path + "ca.crt", path + "client.crt", path + "client.der", "changeit");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
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
			
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(options);
			System.out.println("Connected");
			for (int i = 0; i < 100; i++) {
				String content = "Message from kkkkka-" + System.currentTimeMillis();
				System.out.println("Publishing message: " + content);
				MqttMessage message = new MqttMessage(content.getBytes());
				message.setQos(qos);
				sampleClient.publish(topic, message);
				System.out.println("Message published");
			}
			sampleClient.disconnect();
			System.out.println("Disconnected");
			System.exit(0);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}

	}
}
