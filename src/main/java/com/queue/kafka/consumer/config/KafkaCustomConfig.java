package com.queue.kafka.consumer.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.queue.kafka.consumer.KafkaConsumer;
import com.queue.kafka.consumer.enums.MessageType;
import com.queue.kafka.consumer.interfaces.CustomImplementor;

@Component
public class KafkaCustomConfig {

	private CustomImplementor customImplementor;
	
	@Autowired
	private KafkaConsumer kafkaConsumer;
	
	@Autowired
	private ApplicationContext context;
	
	public void setCustomImplementor(String event) {
		if(customImplementor==null) {
			System.out.println("Event is: "+event);
			customImplementor = (CustomImplementor) context.getBean("testevent");
		}
	}
	
	public void setConsumerWorker(String event) {
		kafkaConsumer.setKafkaWorker(customImplementor.getConsumerWorker(event));
	}

	public MessageType getMessageType(String event) {
		return customImplementor.getMessageType(event);
	}

	public Class getMessageClass(String event) {
		return customImplementor.getMessageClass(event);
	}
	
	public Map<String, Object> getConsumerConfigs(String consumerGroup, String event) {
		return customImplementor.getConsumerConfigs(consumerGroup, event);
	}
}
