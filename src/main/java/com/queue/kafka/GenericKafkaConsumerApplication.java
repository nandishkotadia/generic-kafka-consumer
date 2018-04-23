package com.queue.kafka;

import java.util.Arrays;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.queue.kafka.aspect.Log;
import com.queue.kafka.consumer.ConsumerFactory;
import com.queue.kafka.consumer.enums.QueueType;

@EnableAsync
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class GenericKafkaConsumerApplication implements CommandLineRunner{

	@Autowired
	public ConsumerFactory consumerFactory;
	
	public static @Log Logger logger;
	
	public static void main(String[] args) {
		SpringApplication.run(GenericKafkaConsumerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Input arguments are: "+ Arrays.toString(args));
		String event = args.length>0?args[0]:"testevent";
		QueueType queueType = args.length>1?QueueType.valueOf(args[1].toUpperCase()):QueueType.NORMAL;
		queueType = queueType!=null ? queueType:QueueType.NORMAL;
		consumerFactory.get("kafka").startConsumer(event, queueType, args);
	}

}
