package com.strategicgains.oauth.controllers;


import com.strategicgains.oauth.codegen.dao.UserMapper;
import com.strategicgains.oauth.domain.ContextResult;
import com.strategicgains.oauth.services.IUserService;
import com.strategicgains.oauth.services.impl.UserServiceImpl;
import org.restexpress.Request;
import org.restexpress.Response;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by keesh on 4/26/17.
 */
public class HomeController {

    private String magic;

    private String name;
    private UserServiceImpl userService = null;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserServiceImpl getUserService() {
        return userService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public ContextResult read(Request request, Response response) {

        ContextResult result = new ContextResult();

        Map<String, Object> map = new HashMap<>();


//        result.setData(userService.getUser(1));
        result.setData(666);
        result.setTemplate("/index.twig");

        return result;
    }
}
