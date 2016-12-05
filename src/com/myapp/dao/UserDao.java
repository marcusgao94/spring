package com.myapp.dao;

import com.myapp.model.User;

/**
 * Created by gy on 16/7/8.
 */
public interface UserDao {

    public Boolean saveUser(User user);
    public User getUserByEmailOrUsername(String username);
    public User getUserByEmail(String username);

}
