package com.test.mqtt.activemq.spring.producer;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Producer {

	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @RequestMapping("/send")
    public void send(String msg) {
        jmsMessagingTemplate.convertAndSend(queue, msg);
    }
    
}
