package com.gst.rabbitmq.producer;

import java.io.IOException;

public interface MessageProducer {
	public void sendMessage(Object message) throws IOException;
}
