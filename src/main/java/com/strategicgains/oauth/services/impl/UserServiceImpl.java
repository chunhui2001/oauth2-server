package com.strategicgains.oauth.services.impl;

import com.strategicgains.oauth.services.IUserService;

/**
 * Created by cc on 2017/5/29.
 */
public class UserServiceImpl implements IUserService {

    @Override
    public Object getUserById(int userId) {
        return userId + 999;
    }
}
