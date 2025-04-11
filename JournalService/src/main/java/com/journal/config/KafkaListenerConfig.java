package com.journal.config;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.journal.model.JournalEntry;
import com.user.constrants.KafkaConstants;

@Configuration
@EnableKafka
public class KafkaListenerConfig {

    @Bean
    ConsumerFactory<String, JournalEntry> consumerFactory() {
        JsonDeserializer<JournalEntry> jsonDeserializer = new JsonDeserializer<>(JournalEntry.class);
        jsonDeserializer.addTrustedPackages("com.user.model"); // Ensure trusted deserialization

        Map<String, Object> props = Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BOOTSTRAP_SERVERS,
            ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class
        );

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, JournalEntry> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JournalEntry> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setAutoStartup(true); // Ensures automatic listener startup
        return factory;
    }
}
