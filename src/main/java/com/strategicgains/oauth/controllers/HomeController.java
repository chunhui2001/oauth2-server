package com.strategicgains.oauth.controllers;


import com.strategicgains.oauth.domain.ContextResult;
import com.strategicgains.oauth.entities.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.restexpress.Request;
import org.restexpress.Response;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by keesh on 4/26/17.
 */
public class HomeController {

    private String magic;

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ContextResult read(Request request, Response response) {

        InputStream is = HomeController.class.getClassLoader().getResourceAsStream("mybatisConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);

        SqlSession session = sessionFactory.openSession();

        User user = session.selectOne("com.strategicgains.oauth.entities.mmapping.userMapper.getUser",
                "UU-046baf98-111d-440f-a1e2-a413d711070d");

        ContextResult result = new ContextResult();

        Map<String, Object> map = new HashMap<>();


//        result.setData(userService.getUser(1));

        map.put("userName", user.getPhoto());
        map.put("user", user);

        System.out.println(new com.google.gson.Gson().toJson(user));

        result.setData(map);
        result.setTemplate("/index.twig");

        return result;
    }
}
