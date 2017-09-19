package com.gst.rabbitmq.sms;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gst.rabbitmq.producer.ch3.User;
import com.gst.rabbitmq.sms.service.SmsSendService;
import com.gst.rabbitmq.threadpool.CustomThreadPoolExecutor;
import com.gst.rabbitmq.threadpool.ThreadPool;

/**
 * 短信下行线程
 * 
 * @Description
 * @Project: spring-rabbitmq-producer
 * @Date:Sep 19, 2017
 * @Author dmf
 * @Copyright (c) 2017, 33e9 All Rights Reserved.
 */
@Component
public class SmsSendThread {
	private final static Logger LOG = LoggerFactory.getLogger(SmsSendThread.class);

	@Autowired
	SmsSendService smsSendService;

	public void execute() {
		// Worker worker = new Worker();
		// ThreadPool.start(worker);
		// ThreadPool.destroy();

		CustomThreadPoolExecutor exec = new CustomThreadPoolExecutor();
		// 1.初始化
		exec.init();
		ExecutorService pool = exec.getCustomThreadPoolExecutor();
		for (int i = 1; i < 100; i++) {
			System.out.println("提交第" + i + "个任务!");
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(">>>task is running=====");
						SmsSendThread.this.smsSendService.Send();
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

		// 线程销毁
		exec.destory();

	}//

}
