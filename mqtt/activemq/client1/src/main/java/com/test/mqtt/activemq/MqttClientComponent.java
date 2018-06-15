package com.test.mqtt.activemq;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.mqtt.activemq.conf.MqttProperties;
import com.test.mqtt.activemq.model.PushPayload;

@Component
public class MqttClientComponent {

	@Autowired
    private MqttProperties mqttProperties;
	
	@Autowired
    private  MqttClient mqttClient;
	
	public void push(String topic, PushPayload pushPayload) {
        try {
            // MQTT的连接设置
        	MqttConnectOptions options = new MqttConnectOptions();
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
            // 设置回调
            mqttClient.setCallback(new PushCallback());
            MqttTopic mqttTopic = mqttClient.getTopic(topic);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill(mqttTopic, pushPayload.toString().getBytes(), 2, true);

            mqttClient.connect(options);
            //订阅消息
            int[] Qos  = {1};
            String[] topics = mqttProperties.getTopics();
            mqttClient.subscribe(topics, Qos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
