package com.user.config;

import java.time.Instant;

import com.user.enums.EventType;
import com.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
    private EventType eventType;
    private User user;
    private Instant timestamp;
}