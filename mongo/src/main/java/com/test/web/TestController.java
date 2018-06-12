package com.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.mongo.UserRepository;
import com.test.mongo.dao.UserDao;
import com.test.mongo.entity.UserEntity;

@RestController
public class TestController {

	@Autowired
    private UserDao userDao;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/add/{name}")
	public String add(@PathVariable("name") String name) {
		UserEntity user = new UserEntity();
		Long id = System.currentTimeMillis();
		user.setId(id);
		user.setUserName(name);
		userDao.saveUser(user);
		return "add success, id is " + id;
	}
	
	@RequestMapping("/findByName/{name}")
	public String findByName(@PathVariable("name") String name) {
		UserEntity user = userDao.findUserByUserName(name);
		return user == null ? "" : user.getUserName();
	}
	
	@RequestMapping("/findById/{id}")
	public String findById(@PathVariable("id") Long id) {
		UserEntity user = userDao.findUserById(id);
		return user == null ? "" : user.getUserName();
	}
	
}
