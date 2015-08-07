package com.strategicgains.outh.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.strategicgains.outh.Main;
import com.strategicgains.outh.OAuth2Server;

public class SampleControllerTest {

    /**
     * The REST server that handles the test calls.
     */
    private static OAuth2Server server;
    private HttpClient httpClient;
    private static final String BASE_URL = "http://localhost:8081";


    @BeforeClass
    public static void beforeClass() throws Exception {
        String[] env = { "dev" };
        server = Main.newServer(env);
    }

    @AfterClass
    public static void afterClass() {
        server.shutdown();
    }


    @Before
    public void beforeEach() {
        httpClient = new DefaultHttpClient();
    }


    @After
    public void afterEach() {
        httpClient = null;
    }

    @Test
    public void postDirectiveReplayRequest() throws IOException {
        HttpGet getRequest = new HttpGet(BASE_URL + "/your/route/here/123.json");
        final HttpResponse response = httpClient.execute(getRequest);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }
}
