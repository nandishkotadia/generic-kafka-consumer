package com.queue.kafka.consumer.interfaces;

import com.queue.kafka.consumer.enums.QueueType;

public interface IConsumerInitiator {

	public void startConsumer(String event, QueueType queueType, String... args) throws Exception;
}
