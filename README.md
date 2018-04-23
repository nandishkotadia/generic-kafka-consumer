
# Generic Kafka Consumer

Generic Kafka Consumer is the project developed to ease the usage of Kafka Consumer by developers without them having to know the underlying implementation and use it like a plug & play framework with Spring boot. User can implement Kafka Consumer by just specifying the minimal configuration properties by just implementing the minimal methods. 

**Important parameters:**

> **event** - specifies the event for which the kafka consumer needs to be started. 
> **queueType** - specifies the type of queue.

Above 2 parameters are provides as input arguments while starting the application.

For eg. 
Refer the classes in 

> com.queue.kafka.consumer.example

 package which the user needs to implement.

**Startup Steps:**

 1. To get Kafka Consumer in working state, User first need to implement the CustomImplementor which specifies the custom configuration and the class which implements IWorker which has method which defines how the messages will be processed.

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
			 * This method is used to specify the kafka consumer properties. User can just specify minimal properties and call KafkaConsumerConfig.getConsumerConfigs() to use the code default properties or completely specify own custom Kafka consumer config.
			 * @param consumerGroup
			 * @param event
			 * @return the kafka consumer properties. 
			 */
			Map<String, Object> getConsumerConfigs(String consumerGroup, String event);	
		}

 2. Set the event specific Kafka Consumer properties:

	  a.

	 Set the name of the bean which implements CustomImplementor same as input event.
			

		@Component("testevent") 			
		public class CustomConsumerImplementor implements CustomImplementor{
   				....
    			....
    			....
		}

	b. Set the event specific kafka consumer properties.
	
		kafka.topics.event.testevent=test
		kafka.topics.event.delayed.testevent=delayed-test
		kafka.topics.event.dead.testevent=dead-test
		kafka.consumer.thread.testevent=1

	c. Set the generic kafka consumer properties:

	    bootstrap.servers=localhost:9092



User just need to provide 2 input arguments:

> **event** - specifies the event for which the kafka consumer needs to be started. 
> **queueType** - specifies the type of queue.

**event** parameter is used to get the config related to the kafka consumer.
For eg. if event = 'testevent' then user needs to set following event-specific properties,

    kafka.topics.event.testevent=test
    kafka.topics.event.delayed.testevent=delayed-test
    kafka.topics.event.dead.testevent=dead-test
    kafka.consumer.thread.testevent=1

**Possible queue types:**

> **normal** - this is default & normal consumer state and consumes the messages from actual topic.

> **delayed** - this queuetype consumes the message from delayed topic of the event. Messages are sent to this topic in case of errors and needs to be retried again.

> **dead** - this queuetyoe consumes the messages from dead topic of the event. Messages are sent to this topic after maximum retries are
reached.
