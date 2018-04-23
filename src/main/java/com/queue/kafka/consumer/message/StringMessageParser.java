package com.queue.kafka.consumer.message;

import org.springframework.stereotype.Component;

import com.queue.kafka.consumer.interfaces.IMessageParser;

@Component
public class StringMessageParser<T> implements IMessageParser<T>{

	public String serialize(Object data){
		return (String) data;
	}
	
	public T deserialize(String data, Class<T> classz){
		return (T) data;
	}
}
