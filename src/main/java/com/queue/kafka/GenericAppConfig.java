package com.queue.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
@PropertySources({
		@PropertySource({"classpath:application.properties"}),
		@PropertySource({"classpath:kafka.properties"})
})
public class GenericAppConfig {
	
	public TaskExecutor taskExecutor() {
	    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	    taskExecutor.setCorePoolSize(1);
	    taskExecutor.setMaxPoolSize(1);
	    taskExecutor.setQueueCapacity(1);
	    taskExecutor.afterPropertiesSet();
	    return taskExecutor;
	}
	
	@Autowired
	public Gson gson(){
		return new GsonBuilder().create();
	}
	
}