package com.user.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.config.UserEvent;
import com.user.enums.EventType;
import com.user.enums.RoleType;
import com.user.model.Role;
import com.user.model.User;
import com.user.repository.RoleRepository;
import com.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;    
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;
        	
    public User saveUser(User user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
            .orElseGet(() -> roleRepository.save(new Role(RoleType.ROLE_USER)));
        user.setRoles(Set.of(userRole));  
        
        publishUserEvent(user, EventType.USER_CREATED);
        
        return userRepository.save(user);
    }
    
    private void publishUserEvent(User user, EventType eventType) {
        UserEvent event = new UserEvent(eventType, user, Instant.now());        
        kafkaTemplate.send("user-events", event);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
	@Override
	public Optional<User> updateUser(Long id, User user) {
		return null;
	}
}