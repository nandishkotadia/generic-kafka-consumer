package com.queue.kafka.consumer.example;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.queue.kafka.consumer.config.KafkaConsumerConfig;
import com.queue.kafka.consumer.enums.MessageType;
import com.queue.kafka.consumer.interfaces.CustomImplementor;
import com.queue.kafka.consumer.interfaces.IWorker;

@Component("testevent")
public class CustomConsumerImplementor implements CustomImplementor{

	@Autowired
	private KafkaConsumerWorker consumerWorker;
	
	@Value("${bootstrap.servers}")
	private String bootstrapServers;
	
	@Value("${enable.auto.commit}")
	private boolean enableAutoCommit;
	
	@Value("${auto.commit.interval.ms}")
	private String autoCommitIntervalMS;
	
	@Value("${session.timeout.ms}")
	private String sessionTimeoutMS;
	
	@Value("${auto.offset.reset}")
	private String autoOffsetReset;
	
	@Value("${poll.timeout.ms}")
	private long pollTimeoutMS;
	
	@Value("${max.poll.records}")
	private Integer maxPollRecords;
	
	@Override
	public IWorker getConsumerWorker(String event) {
		return consumerWorker;
	}

	@Override
	public MessageType getMessageType(String event) {
		return MessageType.STRING;
	}

	@Override
	public Class getMessageClass(String event) {
		return null;
	}

	@Override
	public Map<String, Object> getConsumerConfigs(String consumerGroup, String event) {
		return KafkaConsumerConfig.getConsumerConfigs(bootstrapServers, enableAutoCommit, autoCommitIntervalMS, sessionTimeoutMS, autoOffsetReset, maxPollRecords,  consumerGroup, event);
	}

}
