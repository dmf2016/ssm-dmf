package com.gst.rabbitmq.consumer2;

/**
 * 处理消息类
 * 
 * @Description
 * @Project: com.gst.rabbitmq.consumer
 * @Date:Sep 18, 2017
 * @Author dmf
 * @Copyright (c) 2017, 33e9 All Rights Reserved.
 */
public class ReceiveMsgHandler {

	public void handleMessage(String text) {
		System.out.println("Received: " + text);
	}
}
