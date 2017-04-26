package com.strategicgains.oauth.domain;

/**
 * Created by keesh on 4/26/17.
 */
public class StaticResource {

    private String path;
    private Object content;

    public StaticResource() {

    }


    public StaticResource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
