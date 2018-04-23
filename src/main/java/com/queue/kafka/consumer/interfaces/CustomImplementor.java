package com.queue.kafka.consumer.interfaces;

import java.util.Map;

import com.queue.kafka.consumer.enums.MessageType;

/**
 * User needs to implement only the CustomImplementor class to get the Kafka Consumer Working.
 * @author nandish.k
 *
 */
public interface CustomImplementor {

	/**
	 * This method is used to specify the Kafka Worker instance which will process the consumed messages.
	 * @param event
	 * @return the instance of IWorker.
	 */
	public IWorker getConsumerWorker(String event);

	/**
	 * This method is used to get the message type of the message.
	 * Possible message type are 'json' and 'string'
	 * @param event
	 * @return the message type of object.
	 */
	public MessageType getMessageType(String event);

	/**
	 * This method is used only when message type is 'json'.
	 * @param event
	 * @return the class the message needs to be deserialized into if message type is 'json'. Else it is of no use.
	 */
	public Class getMessageClass(String event);

	/**
	 * This method is used to specify the kafka consumer properties.
	 * @param consumerGroup
	 * @param event
	 * @return the kafka consumer properties. 
	 */
	Map<String, Object> getConsumerConfigs(String consumerGroup, String event);

	
}
