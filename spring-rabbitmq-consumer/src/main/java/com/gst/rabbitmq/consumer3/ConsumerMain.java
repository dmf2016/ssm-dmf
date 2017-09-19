package com.gst.rabbitmq.consumer3;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerMain {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("Consumer.xml");
		System.out.println("---消费者启动---");
	}
}
