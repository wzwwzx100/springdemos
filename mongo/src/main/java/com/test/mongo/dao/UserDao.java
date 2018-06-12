package com.test.mongo.dao;

import com.test.mongo.entity.UserEntity;

public interface UserDao {
	
	void saveUser(UserEntity user);
	
	UserEntity findUserByUserName(String userName);
	
	UserEntity findUserById(Long id);
	
	void updateUser(UserEntity user);
	
	void deleteUserById(Long id);

}
