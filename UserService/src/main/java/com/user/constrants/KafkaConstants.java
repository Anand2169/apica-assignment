package com.user.constrants;

/**
 * This class is used to declare constants of this application
 * 
 * @author Anand
 *
 */
public class KafkaConstants {
    public static final String BOOTSTRAP_SERVERS = "localhost:9092"; // Replace with actual Kafka broker address
    public static final String USER_TOPIC = "user-events"; // Topic name for user-related events
    public static final String GROUP_ID = "user-group"; // Consumer group ID for consuming messages
}


