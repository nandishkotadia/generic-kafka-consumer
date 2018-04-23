package com.queue.kafka.consumer.message;

import com.queue.kafka.consumer.interfaces.IMessage;
import com.queue.kafka.consumer.interfaces.IMessageParser;

public class JsonMessage<T> implements IMessage<T>{
	
	private String rawData;
	private IMessageParser<T> messageParser;
	private Class<T> classz;
	private String event;
	private Integer partition;
	
	public JsonMessage(){}
	
	public JsonMessage(String rawData, IMessageParser<T> messageParser, Class<T> classz, String event, Integer partition){
		this.rawData = rawData;
		this.messageParser = messageParser;
		this.classz = classz;
		this.event = event;
		this.partition = partition;
	}
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getRawData() {
		return rawData;
	}
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
	public IMessageParser<T> getMessageParser() {
		return messageParser;
	}
	public void setMessageParser(IMessageParser<T> messageParser) {
		this.messageParser = messageParser;
	}
	public Class<T> getClassz() {
		return classz;
	}
	public void setClassz(Class<T> classz) {
		this.classz = classz;
	}
	@Override
	public Integer getPartition() {
		return partition;
	}
	public void setPartition(Integer partition) {
		this.partition = partition;
	}
	
	@Override
	public T getDeserializedObject(){
		return messageParser.deserialize(rawData, classz);
	}

	@Override
	public T getDeserializedObject(Class<T> customclassz) {
		return messageParser.deserialize(rawData, customclassz);
	}

}
