package com.gst.rabbitmq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Send {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-rabbitmq-async-send.xml");
		AmqpTemplate amqpTemplate = context.getBean(RabbitTemplate.class);
		for (int i = 0; i < 1000; i++) {
			amqpTemplate.convertAndSend("test spring async=>" + i);
			System.out.println("send start:"+i);
			Thread.sleep(1000);
		}
	}
}