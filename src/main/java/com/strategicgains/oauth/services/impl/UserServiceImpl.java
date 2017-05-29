package com.strategicgains.oauth.services.impl;

import com.strategicgains.oauth.codegen.dao.UserMapper;
import com.strategicgains.oauth.codegen.model.User;
import com.strategicgains.oauth.repository.UserDao;
import com.strategicgains.oauth.services.IUserService;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cc on 2017/5/29.
 */
public class UserServiceImpl {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUser(int id) {

        try {
            return userDao.selectByPrimaryKey(id + "");
        } catch (Exception e) {
            return null;
        }

    }

}
