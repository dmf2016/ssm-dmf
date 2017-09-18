package com.gst.rabbitmq.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Receive {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("applicationContext-rabbitmq-async-receive.xml");
		System.out.println("---Receive--start:---");
	}
}
