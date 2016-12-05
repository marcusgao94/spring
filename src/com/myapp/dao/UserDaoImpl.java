package com.myapp.dao;

import com.myapp.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by gy on 16/7/8.
 */

@Repository
public class UserDaoImpl implements UserDao {

    private static Log logger = LogFactory.getLog(UserDaoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    //public static final String COLLECTION_NAME = "user";

    @Override
    public Boolean saveUser(User user) {
        logger.info("save user");
        mongoTemplate.insert(user);
        return true;
    }

    @Override
    public User getUserByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public User getUserByEmailOrUsername(String username) {
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("username").is(username),
                Criteria.where("email").is(username));
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, User.class);
    }
}
