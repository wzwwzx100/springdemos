package com.test.mongo.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.test.mongo.dao.UserDao;
import com.test.mongo.entity.UserEntity;
import com.test.utils.ThrowableUtil;

@Component
public class UserDaoImpl implements UserDao {
	
	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

	@Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     * @param user
     */
    @Override
    public void saveUser(UserEntity user) {
    	logger.info("save user...");
        mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */
    @Override
    public UserEntity findUserByUserName(String userName) {
    	logger.info("username:::: " + userName);
        Query query=new Query(Criteria.where("userName").is(userName));
        UserEntity user =  mongoTemplate.findOne(query , UserEntity.class);
        return user;
    }
    
    @Override
    public UserEntity findUserById(Long id) {
    	logger.info("id:::: " + id);
        Query query=new Query(Criteria.where("id").is(id));
        UserEntity user =  mongoTemplate.findOne(query , UserEntity.class);
        logger.info("username::::: " + user.getUserName());
        return user;
    }

    /**
     * 更新对象
     * @param user
     */
    @Override
    public void updateUser(UserEntity user) {
    	logger.info("update user ....");
        Query query=new Query(Criteria.where("id").is(user.getId()));
        Update update= new Update().set("userName", user.getUserName()).set("passWord", user.getPassWord());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,UserEntity.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
    }

    /**
     * 删除对象
     * @param id
     */
    @Override
    public void deleteUserById(Long id) {
    	logger.info("delete user id = " + id);
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,UserEntity.class);
        try {
        int i = 0;
        int a = 5 / i;
        } catch(Exception e) {
        	String err = ThrowableUtil.getErrorInfoFromThrowable(e);
        	System.out.println(err);
        	CharSequence charSequence;
        	charSequence = err;
        	System.out.println(charSequence);
        	Message sm = new SimpleMessage(err);
        	logger.error(sm);
        }
    }
    
}
