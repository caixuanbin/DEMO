package com.xbcai.myweb.dao.impl;

import com.xbcai.myweb.dao.UserDao;
import com.xbcai.myweb.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public Object findObject(String name, String job) {
        User u = new User(name,job);
        return u;
    }
}
