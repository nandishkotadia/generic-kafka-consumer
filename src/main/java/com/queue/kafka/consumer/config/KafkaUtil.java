package com.queue.kafka.consumer.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.queue.kafka.consumer.enums.QueueType;

@Component
public class KafkaUtil {

	@Autowired
	private Environment environment;
	
	public List<String> getTopics(String event, QueueType queueType) throws Exception {
		String topicStr = null;
		if(QueueType.DEAD == queueType){
			topicStr = getDeadTopic(event);
		} else if(QueueType.DELAYED == queueType) {
			topicStr = getDelayedTopic(event);
		} else {
			topicStr = environment.getProperty("kafka.topics.event."+event);
		}
		if(!StringUtils.isEmpty(topicStr)){
			return Arrays.asList(topicStr.split(","));
		}
		throw new Exception("kafka topics not configured in kafka.properties for event: "+event);
	}
	
	public String getDeadTopic(String event) throws Exception {
		String topic = environment.getProperty("kafka.topics.event.dead."+event);
		if(!StringUtils.isEmpty(topic)){
			return topic;
		}
		throw new Exception("kafka dead topic not configured in kafka.properties for event: "+event);
	}
	
	public String getDelayedTopic(String event) throws Exception {
		String topic = environment.getProperty("kafka.topics.event.delayed."+event);
		if(!StringUtils.isEmpty(topic)){
			return topic;
		}
		throw new Exception("kafka dead topic not configured in kafka.properties for event: "+event);
	}
	
	public String getConsumerGroup() throws Exception {
		String consumerGroup = environment.getProperty("kafka.consumer.group");
		if(!StringUtils.isEmpty(consumerGroup)){
			return consumerGroup;
		}
		throw new Exception("kafka topics consumer group not configured in kafka.properties.");
	}
	
	public Integer getConsumerThreads(String event) throws Exception {
		String consumerThreads = environment.getProperty("kafka.consumer.thread."+event);
		if(!StringUtils.isEmpty(consumerThreads)){
			return Integer.parseInt(consumerThreads);
		}
		throw new Exception("kafka consumer threads count not configured in kafka.properties for event: "+event);
	}

}
