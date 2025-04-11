package com.journal.event;

import java.time.Instant;

import com.journal.enums.EventType;
import com.journal.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
    private EventType eventType; // Enum for event type (e.g., USER_CREATED)
    private User user;           // User object associated with the event
    private Instant timestamp;   // Timestamp for the event
}