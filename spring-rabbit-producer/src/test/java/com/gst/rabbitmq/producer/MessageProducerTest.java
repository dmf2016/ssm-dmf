package com.gst.rabbitmq.producer;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring-content.xml" })
public class MessageProducerTest {

	private Logger LOG = LoggerFactory.getLogger(MessageProducerTest.class);

	@Autowired
	private MessageProducer messageProducer;

	@Test
	public void testSendMessage() throws IOException {
		int a = 100;
		while (a > 0) {
			messageProducer.sendMessage("Hello, I am amq sender num :" + a--);
			try {
				// 暂停一下，好让消息消费者去取消息打印出来
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
