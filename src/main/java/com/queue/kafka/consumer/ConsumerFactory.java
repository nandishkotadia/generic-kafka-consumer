package com.queue.kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.queue.kafka.consumer.KafkaConsumerInitiator;
import com.queue.kafka.consumer.interfaces.IConsumerInitiator;

@Component
public class ConsumerFactory {
	
	@Autowired
	private KafkaConsumerInitiator kafkaConsumerInitiator;
	
	public IConsumerInitiator get(String consumerType){
		switch(consumerType){
			case "kafka": return kafkaConsumerInitiator;
			default: return null;
		}
	}
	
}
