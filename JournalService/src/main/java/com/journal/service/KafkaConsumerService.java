package com.journal.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.journal.event.UserEvent;
import com.journal.model.JournalEntry;
import com.journal.model.User;
import com.journal.repository.JournalEntryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final JournalEntryRepository journalEntryRepository;
    private final ObjectMapper objectMapper; // For JSON conversion

    @KafkaListener(topics = "user-events", groupId = "journal-group")
    public void consume(UserEvent event) {
        JournalEntry entry = new JournalEntry();
        entry.setEventType(event.getEventType().name());
        entry.setUserId(event.getUser().getId());
        entry.setTimestamp(event.getTimestamp());
        entry.setPayload(convertToJson(event.getUser())); // Using the JSON conversion method
        journalEntryRepository.save(entry);
    }

    private String convertToJson(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            throw new RuntimeException("Error converting User object to JSON", e);
        }
    }
}
