package com.test.mqtt.emq.conf;

import javax.net.ssl.SSLSocketFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Header;

import com.test.mqtt.emq.util.SslUtil;

@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfiguration {
	
	private static Logger logger = LogManager.getLogger(MqttConfiguration.class);

	@Autowired
	private MqttProperties mqttProperties;

	// ---------------------------------input--------------------------------------
	@Bean
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer inbound() {
		String clientId = mqttProperties.getClientIdRecive() + System.currentTimeMillis();
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
		        mqttProperties.getHosts()[0], clientId, mqttProperties.getReciveTopics());
		adapter.setCompletionTimeout(mqttProperties.getCompletionTimeout());
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	public MessageHandler handler() {
		return new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				MessageHeaders headers = message.getHeaders();
				String topic = (String) headers.get("mqtt_receivedTopic");
				logger.info("listen topic: " + topic + ", payload: " + message.getPayload());
			}
		};
	}

	// ----------------------output------------------------------------
	@Bean
	public MqttPahoClientFactory clientFactory() throws Exception {
		DefaultMqttPahoClientFactory clientFactory = new DefaultMqttPahoClientFactory();

		MqttConnectOptions options = new MqttConnectOptions();

		SSLSocketFactory factory = SslUtil.getSocketFactory(mqttProperties.getCaCertificate(), mqttProperties.getCertificate(), mqttProperties.getPrivateKey(), mqttProperties.getKeyPass());
		
		options.setSocketFactory(factory);
		
		options.setServerURIs(mqttProperties.getHosts());
		// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
		options.setCleanSession(mqttProperties.isCleanSession());
		// 设置连接的用户名
		options.setUserName(mqttProperties.getUsername());
		// 设置连接的密码
		options.setPassword(mqttProperties.getPassword().toCharArray());
		// 设置超时时间 单位为秒
		options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
		// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
		options.setKeepAliveInterval(mqttProperties.getKeepalive());
		//设置遗嘱消息
		options.setWill("willTopic", "I'm offline.".getBytes("utf-8"), 2, false);
		
		clientFactory.setConnectionOptions(options);
		return clientFactory;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	public MessageHandler mqttOutbound() throws Exception {
		String clientId= mqttProperties.getCaCertificate() + System.currentTimeMillis();
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId,
		        clientFactory());
		messageHandler.setAsync(mqttProperties.isAsync());
		messageHandler.setDefaultTopic(mqttProperties.getDefaultTopic());
		return messageHandler;
	}

	@Bean
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}

	@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
	public interface MyGateway {
		void sendToMqtt(String payload, @Header(name=MqttHeaders.TOPIC) String topic);
	}

}
