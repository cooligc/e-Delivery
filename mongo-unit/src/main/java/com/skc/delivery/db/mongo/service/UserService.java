package com.skc.delivery.db.mongo.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.skc.delivery.db.mongo.user.User;

/**
 * <p> User Service to create user </p>
 * */
@Service("userService")
public class UserService {
	private final static Logger LOGGER = Logger.getLogger(UserService.class);
	
	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * <p> Method to create an user</p>
	 * */
	public User createUser(User user){
		LOGGER.info(user+" is going to be created");
		MongoOperations mongoOperations = mongoTemplate;
		mongoOperations.insert(user);
		LOGGER.info("User created successfully");
		return user;
	}
}
