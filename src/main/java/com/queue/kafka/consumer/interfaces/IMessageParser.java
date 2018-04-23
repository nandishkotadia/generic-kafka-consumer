package com.queue.kafka.consumer.interfaces;

public interface IMessageParser<T> {
	
	public String serialize(Object data);
	
	public T deserialize(String data, Class<T> classz);
	
}
