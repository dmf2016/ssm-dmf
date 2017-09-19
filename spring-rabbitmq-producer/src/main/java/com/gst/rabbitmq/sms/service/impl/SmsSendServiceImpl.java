package com.gst.rabbitmq.sms.service.impl;

import java.util.Random;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gst.rabbitmq.producer.ch3.User;
import com.gst.rabbitmq.sms.service.SmsSendService;

@Service
public class SmsSendServiceImpl implements SmsSendService {

	@Autowired
	AmqpTemplate amqpTemplate;

	@Override
	public void Send() {
		Random random = new Random();
		int id = random.nextInt(10000);
		User user = new User();
		user.setId(id);
		user.setAge(28);
		user.setName("dmf" + id);
		amqpTemplate.convertAndSend(user);
	}

}
