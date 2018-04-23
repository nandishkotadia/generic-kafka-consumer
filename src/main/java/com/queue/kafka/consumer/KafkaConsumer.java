package com.queue.kafka.consumer;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.queue.kafka.aspect.Log;
import com.queue.kafka.consumer.interfaces.IMessage;
import com.queue.kafka.consumer.interfaces.IMessageParser;
import com.queue.kafka.consumer.interfaces.IWorker;
import com.queue.kafka.consumer.message.MessageParserFactory;

@Component
public class KafkaConsumer {
	
	private static @Log Logger logger;
	
	@Value("${poll.timeout.ms}")
	private long pollTimeoutMS;//100
	
	private IWorker kafkaWorker;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private MessageParserFactory messageParserFactory;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Async
	public void startConsumer(Consumer consumer, IMessageParser messageParser, Class classz, String event) {
		try{
			while(true){
				ConsumerRecords<String, String> records = consumer.poll(pollTimeoutMS);
				
				if(!records.isEmpty()){
					List<IMessage> messages = new ArrayList<>();
					for(ConsumerRecord record:records){
						String message = (String) record.value();
						IMessage msg = messageParserFactory.createMessage(message,messageParser, classz, event,record.partition()) ;
						messages.add(msg);
					}
					kafkaWorker.doWork(messages);
					//manual commit
					consumer.commitSync();
				}
			}
		}catch(Exception e){
			consumer.close();
			logger.error("Error in message consuming",e);
			KafkaConsumerInitiator.aliveQueues--;
			logger.info("Number of alive consumer left: "+KafkaConsumerInitiator.aliveQueues);
			if(KafkaConsumerInitiator.aliveQueues==0){
				System.exit(1);
			}
		}
	}

	public void setKafkaWorker(IWorker kafkaWorker) {
		this.kafkaWorker = kafkaWorker;
	}

	
	
}

/*logger.info("Receive message: " + message + ", Partition: "
					            + record.partition() + ", Offset: " + record.offset() + ", by ThreadID: "
					            + Thread.currentThread().getId()+ ", key: "+record.key());*/