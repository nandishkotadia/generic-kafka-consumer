package com.queue.kafka.consumer.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.queue.kafka.consumer.interfaces.IMessageParser;

@Component
public class JsonMessageParser<T> implements IMessageParser<T>{

	@Autowired
	private Gson gson;
	
	public String serialize(Object data){
		return gson.toJson(data);
	}
	
	public T deserialize(String data, Class<T> classz){
		return gson.fromJson(data, classz);
	}

}
