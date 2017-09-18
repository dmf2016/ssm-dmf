package com.gst.rabbitmq.consumer;

public class ReceiveMsgHandler {

	public void handleMessage(String text) {
		System.out.println("Received: " + text);
	}
}
