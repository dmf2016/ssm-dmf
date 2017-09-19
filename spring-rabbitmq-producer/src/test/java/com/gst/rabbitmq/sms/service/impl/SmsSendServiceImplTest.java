package com.gst.rabbitmq.sms.service.impl;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gst.rabbitmq.sms.service.SmsSendService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:Producer.xml" })
public class SmsSendServiceImplTest {
	// 自增整数
	private final AtomicInteger counter = new AtomicInteger();

	@Autowired
	SmsSendService smsSendService;

	@Test
	public void testSend() {

		while (true) {
			System.out.println("-------start----"+counter.incrementAndGet());
			smsSendService.Send();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
