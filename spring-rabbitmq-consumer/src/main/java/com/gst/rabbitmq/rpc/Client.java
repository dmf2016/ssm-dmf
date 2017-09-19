package com.gst.rabbitmq.rpc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gst.rabbitmq.rpc.service.TestService;

public class Client {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-rabbitmq-rpc-client.xml");
		TestService testService = (TestService) context.getBean("testService");
		System.out.println(testService.say(" Tom"));
	}

}