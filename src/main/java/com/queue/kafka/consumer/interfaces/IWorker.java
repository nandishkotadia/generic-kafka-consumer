package com.queue.kafka.consumer.interfaces;

import java.util.List;

public interface IWorker {

	public void doWork(List<IMessage> messages);
}
