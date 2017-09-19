package com.gst.rabbitmq.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadPoolExecutor {

	private ThreadPoolExecutor pool = null;

	/**
	 * 核心线程池大小
	 */
	private static int corePoolSize = 10;

	/**
	 * 最大线程池大小
	 */
	private static int maximumPoolSize = 30;

	private static int keepAliveTime = 30;

	/**
	 * 线程池初始化方法 ----newArrayBlockingQueue<Runnable> (5)====5容量的阻塞队列
	 * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
	 * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
	 * 即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
	 * 任务会交给RejectedExecutionHandler来处理
	 */
	public void init() {
		pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MINUTES,
				new ArrayBlockingQueue<Runnable>(5), new CustomThreadFactory(), new CustomRejectedExecutionHandler());
	}

	public void destory() {
		if (pool != null) {
			pool.shutdownNow();
		}
	}

	public ExecutorService getCustomThreadPoolExecutor() {
		return this.pool;
	}

	/**
	 * 新建线程工厂
	 * 
	 * @Description
	 * @Project: spring-rabbitmq-producer
	 * @Date:Sep 19, 2017
	 * @Author dmf
	 * @Copyright (c) 2017, 33e9 All Rights Reserved.
	 */
	private class CustomThreadFactory implements ThreadFactory {
		private AtomicInteger count = new AtomicInteger(0);

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			String threadName = CustomThreadPoolExecutor.class.getSimpleName() + count.addAndGet(1);
			System.out.println(threadName);
			t.setName(threadName);
			return t;
		}
	}

	/**
	 * 当提交任务数超过maxmumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
	 * 
	 * @Description
	 * @Project: spring-rabbitmq-producer
	 * @Date:Sep 19, 2017
	 * @Author dmf
	 * @Copyright (c) 2017, 33e9 All Rights Reserved.
	 */
	private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try {
				// 核心改造点，由blockingqueue的offer改成put阻塞方法
				executor.getQueue().put(r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 测试构造的线程池
	public static void main(String[] args) {

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
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

		// 2.销毁----此处不能销毁,因为任务没有提交执行完,如果销毁线程池,任务也就无法执行了
		// exec.destory();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
