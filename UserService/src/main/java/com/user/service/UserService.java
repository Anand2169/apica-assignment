package com.user.service;

import java.util.List;
import java.util.Optional;

import com.user.model.User;

public interface UserService {
	User saveUser(User user);
	List<User> getAllUsers();
	Optional<User> getUserById(Long id);
	Optional<User> updateUser(Long id, User user);
	void deleteUser(Long id);
}
