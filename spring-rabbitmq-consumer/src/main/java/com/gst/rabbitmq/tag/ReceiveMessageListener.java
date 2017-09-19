package com.gst.rabbitmq.tag;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 消息监听
 * 
 * @Description
 * @Project: com.gst.rabbitmq.consumer
 * @Date:Sep 19, 2017
 * @Author dmf
 * @Copyright (c) 2017, 33e9 All Rights Reserved.
 */
public class ReceiveMessageListener implements MessageListener {

	@Override
	public void onMessage(Message msg) {
		System.out.println("---:" + msg);
	}

}
