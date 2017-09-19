package com.gst.rabbitmq.consumer2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Consumer {
	public static void main(String[] args) {
		new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
	}
}
