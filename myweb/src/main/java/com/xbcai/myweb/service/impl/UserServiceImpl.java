package com.xbcai.myweb.service.impl;

import com.xbcai.myweb.aop.ServiceLogs;
import com.xbcai.myweb.dao.UserDao;
import com.xbcai.myweb.model.User;
import com.xbcai.myweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public Object findObject(String name, String job) {
        return userDao.findObject(name,job);
    }

    @ServiceLogs(description = "service 层 查询用户信息")
    @Override
    public Object findObject2(String name, String job) {
        int i = 1/0;
        return new User(name,job);
    }
}
