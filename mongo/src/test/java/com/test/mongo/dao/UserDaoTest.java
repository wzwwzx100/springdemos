package com.test.mongo.dao;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.mongo.UserRepository;
import com.test.mongo.entity.UserEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

	@Autowired
    private UserDao userDao;
	
	@Autowired
	private UserRepository userRepository;

//    @Test
    public void testSaveUser() throws Exception {
        UserEntity user=new UserEntity();
        user.setId(2L);
        user.setUserName("小明");
        user.setPassWord("fffooo123");
        userDao.saveUser(user);
    }

    @Test
    public void findUserById(){
       UserEntity user= userDao.findUserById(2L);
       System.out.println("user is " + user);
       System.out.println("user name is " + user.getUserName());
       Assert.assertEquals("天空", user.getUserName());
    }

//    @Test
    public void updateUser(){
        UserEntity user=new UserEntity();
        user.setId(2L);
        user.setUserName("天空");
        user.setPassWord("fffxxxx");
        userDao.updateUser(user);
    }

//    @Test
    public void deleteUserById(){
        userDao.deleteUserById(1L);
    }
    
}
