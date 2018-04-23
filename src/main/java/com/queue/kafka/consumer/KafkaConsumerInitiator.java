package com.queue.kafka.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

import com.queue.kafka.aspect.Log;
import com.queue.kafka.consumer.config.KafkaConsumerConfig;
import com.queue.kafka.consumer.config.KafkaCustomConfig;
import com.queue.kafka.consumer.config.KafkaUtil;
import com.queue.kafka.consumer.enums.MessageType;
import com.queue.kafka.consumer.enums.QueueType;
import com.queue.kafka.consumer.interfaces.IConsumerInitiator;
import com.queue.kafka.consumer.interfaces.IMessageParser;
import com.queue.kafka.consumer.message.MessageParserFactory;

@Component
public class KafkaConsumerInitiator implements IConsumerInitiator{

	@Autowired
	private KafkaConsumerConfig kafkaConsumerConfig;

	@Autowired
	private ApplicationContext appCtx;

	@Autowired
	private KafkaUtil kafkaUtil;

	private static @Log Logger logger;

	@SuppressWarnings("rawtypes")
	@Autowired
	private MessageParserFactory messageParserFactory;
	
	public static int aliveQueues=0;
	
	@Autowired
	private KafkaCustomConfig kafkaCustomConfig;
	
	
	@Override
	public void startConsumer(String event, QueueType queueType, String... args) throws Exception{
		
		String consumerGroup = kafkaUtil.getConsumerGroup();
		int consumerThreads = kafkaUtil.getConsumerThreads(event);
		List<String> topicList = kafkaUtil.getTopics(event, queueType);
		if(topicList.size()==0){
			throw new Exception("Topics not configured in kafka.properties");
		}
		kafkaUtil.getDeadTopic(event);//This will validate dead topic and will throw error in case of no topic configured
		kafkaUtil.getDelayedTopic(event);//This will validate delayed topic and will throw error in case of no topic configured
		logger.info("Topics: "+topicList);
		initiateConsumer(consumerGroup, consumerThreads, topicList, event);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initiateConsumer(String consumerGroup, int consumerThreads, List<String> topicList, String event){
		
		//To set the customimplementor
		kafkaCustomConfig.setCustomImplementor(event);
		
		MessageType messageType = kafkaCustomConfig.getMessageType(event);
		IMessageParser messageParser = messageParserFactory.get(messageType);
		Class classz = kafkaCustomConfig.getMessageClass(event);
		if(MessageType.JSON== messageType && classz == null){
			logger.info("No classz found for event. Please provide the definition for same in kafkaCustomConfig.getMessageClass()");
			return;
		}
		KafkaConsumer consumer = getMessageProcessor(event);
		
		ConcurrentKafkaListenerContainerFactory factory = kafkaConsumerConfig.kafkaListenerContainerFactory(consumerGroup, consumerThreads, event);
		for(int i =0;i<consumerThreads;i++){
			Consumer cons = factory.getConsumerFactory().createConsumer();
			cons.subscribe(topicList);
			consumer.startConsumer(cons, messageParser, classz, event);
			aliveQueues++;
		}
	}
	
	public KafkaConsumer getMessageProcessor(String event) {
		switch(event){
		 	default: {
		 		KafkaConsumer consumer =  appCtx.getBean(KafkaConsumer.class);
		 		kafkaCustomConfig.setConsumerWorker(event);
		 		return consumer;
		 	}
	    }
	}
}
