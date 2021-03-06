package com.test.mqtt.activemq.conf;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfiguration {

	@Autowired
	private MqttProperties mqttProperties;

	@Bean
	public MqttPahoClientFactory clientFactory() {
		DefaultMqttPahoClientFactory clientFactory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(mqttProperties.getHosts());
		// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
		options.setCleanSession(true);
		// 设置连接的用户名
		options.setUserName(mqttProperties.getUsername());
		// 设置连接的密码
		options.setPassword(mqttProperties.getPassword().toCharArray());
		// 设置超时时间 单位为秒
		options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
		// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
		options.setKeepAliveInterval(mqttProperties.getKeepalive());
		clientFactory.setConnectionOptions(options);
		return clientFactory;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	public MessageHandler mqttOutbound() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("testClient", clientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic(mqttProperties.getTopics()[0]);
		return messageHandler;
	}

	@Bean
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}

	@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
	public interface MyGateway {
		void sendToMqtt(String data);
	}

}
