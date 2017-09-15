package com.gst.rabbitmq.producer.impl;

import java.io.IOException;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.gst.rabbitmq.producer.MessageProducer;

@Service
public class MessageProducerImpl implements MessageProducer {
	private Logger logger = LoggerFactory.getLogger(MessageProducerImpl.class);

	@Resource(name = "amqpTemplate")
	private AmqpTemplate amqpTemplate;

	public void sendMessage(Object message) throws IOException {
		logger.info("to send message:{}", message);
		amqpTemplate.convertAndSend("queueTestKey", message);
		amqpTemplate.convertAndSend("queueTestChris", message);
	}
}
