package com.queue.kafka.consumer.example;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.queue.kafka.aspect.Log;
import com.queue.kafka.consumer.interfaces.IMessage;
import com.queue.kafka.consumer.interfaces.IWorker;

@Component
public class KafkaConsumerWorker implements IWorker{

	private static @Log Logger logger;
	
	public CountDownLatch countDownLatch;
	
	@SuppressWarnings("rawtypes")
	public void doWork(List<IMessage> messages) {
		try {
			long currentTime = System.currentTimeMillis();
			//countDownLatch = new CountDownLatch(messages.size());
			for(IMessage message:messages){
				//perform task
				System.out.println(message.getDeserializedObject());
			}
			logger.info("Waiting to process record/message...");
			//countDownLatch.await();
			logger.info("Successfully processed record/message...");
			logger.info("Overall TimeTaken:"+(System.currentTimeMillis()-currentTime));
			logger.info("Successfully processed records/meesages with size:" + (messages.size()) );
		} catch (Exception e) {
			logger.error("Error...",e);
			System.exit(1);
		}
	}

}
