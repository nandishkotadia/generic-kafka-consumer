package com.queue.kafka.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

import com.queue.kafka.aspect.Log;


@Component
public class KafkaConsumerConfig {
	
	@Value("${poll.timeout.ms}")
	private long pollTimeoutMS;
	
	@Autowired
	private KafkaCustomConfig kafkaCustomConfig;
	
	private static @Log Logger logger;
	
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(String consumerGroup, int consumerThreads, String event) {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory(consumerGroup,event));
		factory.setConcurrency(consumerThreads);
		factory.getContainerProperties().setPollTimeout(pollTimeoutMS);
		return factory;
	}
 
	public ConsumerFactory<String, String> consumerFactory(String consumerGroup, String event) {
		return new DefaultKafkaConsumerFactory<>(kafkaCustomConfig.getConsumerConfigs(consumerGroup, event));
	}
	
	public static Map<String, Object> getConsumerConfigs(String bootstrapServers, boolean enableAutoCommit, String autoCommitIntervalMS, String sessionTimeoutMS, String autoOffsetReset, Integer maxPollRecords, String consumerGroup, String event) {
		Map<String, Object> propsMap = new HashMap<>();
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMS);
		propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMS);
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
		propsMap.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, "65000");
		propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000");
		
		propsMap.put(ConsumerConfig.RECEIVE_BUFFER_CONFIG,"1048576");
		propsMap.put(ConsumerConfig.SEND_BUFFER_CONFIG,"1048576");
		String clientId = event+"-"+consumerGroup;
		propsMap.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
		propsMap.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "2147483647");
		return propsMap;
	}
 
}