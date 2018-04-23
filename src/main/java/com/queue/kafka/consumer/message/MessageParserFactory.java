package com.queue.kafka.consumer.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.queue.kafka.consumer.enums.MessageType;
import com.queue.kafka.consumer.interfaces.IMessage;
import com.queue.kafka.consumer.interfaces.IMessageParser;

@Component
public class MessageParserFactory<T>{
	
	@Autowired
	private JsonMessageParser<T> jsonMessageParser;
	
	@Autowired
	private StringMessageParser<T> stringMessageParser;

	public IMessageParser<T> get(MessageType messageType){
		switch(messageType){
			case JSON: return jsonMessageParser;
			default: return stringMessageParser;
		}
	}
	
	public IMessage<T> createMessage(String message,IMessageParser<T> messageParser, Class<T> classz, String event, Integer partition) {
		return new JsonMessage<T>(message,messageParser,classz,event,partition);
	}

}
