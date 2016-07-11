package com.myapp.service;

import com.myapp.dao.UserDaoImpl;
import com.myapp.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by gy on 16/7/8.
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDaoImpl userDao;

    private static Log logger = LogFactory.getLog(UserServiceImpl.class);

    @Override
    public boolean saveUser(User user) {
        logger.info("save user");
        return userDao.saveUser(user);
    }
}
