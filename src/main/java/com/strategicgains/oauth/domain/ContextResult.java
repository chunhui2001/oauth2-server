package com.strategicgains.oauth.domain;

import java.util.Map;

/**
 * Created by keesh on 4/26/17.
 */
public class ContextResult {

    private boolean error = false;
    private Object data = null;
    private String[] message = null;
    private Integer code = 0;
    private String template = null;


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
