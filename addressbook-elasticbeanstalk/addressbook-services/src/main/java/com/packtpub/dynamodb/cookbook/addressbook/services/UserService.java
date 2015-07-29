package com.packtpub.dynamodb.cookbook.addressbook.services;

import org.springframework.stereotype.Service;

import com.packtpub.dynamodb.cookbook.addressbook.domain.User;

@Service
public interface UserService {

	void createUser(User user);

	User getUserByEmail(String email);
}
