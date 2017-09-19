package com.gst.rabbitmq.threadpool;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 例子
 * 
 * @Description
 * @Project: com.gst.rabbitmq.consumer
 * @Date:Sep 19, 2017
 * @Author dmf
 * @Copyright (c) 2017, 33e9 All Rights Reserved.
 */
public class WorkerItem implements Runnable, Serializable {
	private final static Logger LOG = LoggerFactory.getLogger(WorkerItem.class);
	private static final long serialVersionUID = 1L;

	/**
	 * 执行任务
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(ThreadPool.SLEEP_TIME);
			System.out.print("任务执行了");
		} catch (InterruptedException e) {
			LOG.error("run:", e);
		}

	}

}
