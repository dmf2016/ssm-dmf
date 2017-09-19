package com.gst.rabbitmq.producer.rpc.service.impl;

import com.gst.rabbitmq.producer.rpc.service.TestService;

/**
 * rpc 实现类
 * 
 * @Description
 * @Project: spring-rabbitmq-producer
 * @Date:Sep 18, 2017
 * @Author dmf
 * @Copyright (c) 2017, 33e9 All Rights Reserved.
 */
public class TestServiceImpl implements TestService {

	public String say(String msg) {
		return "hello " + msg;
	}
}
