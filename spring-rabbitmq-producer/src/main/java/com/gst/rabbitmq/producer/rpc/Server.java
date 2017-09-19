package com.gst.rabbitmq.producer.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("applicationContext-rabbitmq-rpc-server.xml");
		System.out.println("----server---start---");
	}
}
