package com.gst.rabbitmq.producer.ch2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Producer {

	public static void main(String[] args) {
		new AnnotationConfigApplicationContext(ProducerConfiguration.class);
	}
}
