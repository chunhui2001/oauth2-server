package com.strategicgains.oauth.controllers;


import com.strategicgains.oauth.domain.ContextResult;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by keesh on 4/26/17.
 */
public class HomeController {

    public ContextResult read(Request request, Response response) {

        ContextResult result = new ContextResult();

        Map<String, Object> map = new HashMap<>();

        map.put("userName", "张春辉");

        result.setData(map);
        result.setTemplate("/index.twig");

        return result;
    }
}
