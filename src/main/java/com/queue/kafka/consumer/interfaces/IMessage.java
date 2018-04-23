package com.queue.kafka.consumer.interfaces;

public interface IMessage<T> {

	public T getDeserializedObject();
	public String getEvent();
	public String getRawData();
	public Integer getPartition();
	public T getDeserializedObject(Class<T> classz);
}
