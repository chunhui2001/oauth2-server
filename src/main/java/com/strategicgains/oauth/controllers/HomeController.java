package com.strategicgains.oauth.controllers;


import com.strategicgains.oauth.domain.ContextResult;
import com.strategicgains.oauth.services.IUserService;
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

    @Value("${driver}")
    private String magic;

    private String name;
    private IUserService userService = null;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public ContextResult read(Request request, Response response) {

        ContextResult result = new ContextResult();

        Map<String, Object> map = new HashMap<>();

        map.put("userName", ".. 张春辉8665hhh" + this.getName() + " " + userService.getUserById(1) + this.magic);

        result.setData(map);
        result.setTemplate("/index.twig");

        return result;
    }
}
