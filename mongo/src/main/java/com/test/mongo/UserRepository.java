package com.test.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.mongo.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

	UserEntity findById(Long id);
	
}
