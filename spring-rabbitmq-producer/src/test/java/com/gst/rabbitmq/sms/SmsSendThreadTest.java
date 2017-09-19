package com.gst.rabbitmq.sms;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:Producer.xml" })
public class SmsSendThreadTest {

	@Autowired
	SmsSendThread smsSendThread;

	@Test
	public void testExecute() {
		// 通过注入的Bean启动线程
		smsSendThread.execute();
	}

}
