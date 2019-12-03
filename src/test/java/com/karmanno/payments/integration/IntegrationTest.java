package com.karmanno.payments.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmanno.payments.Application;
import io.logz.guice.jersey.JerseyServer;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class IntegrationTest {
    private DefaultHttpClient httpClient;
    ObjectMapper objectMapper;
    private static JerseyServer jerseyServer;

    @BeforeEach
    public void before() {
        httpClient = new DefaultHttpClient();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void after() {
        httpClient.getConnectionManager().shutdown();
    }

    @BeforeAll
    public static void beforeAll() throws Exception {
        jerseyServer = Application.createJerseyServer(9001, true);
        jerseyServer.start();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        jerseyServer.stop();
    }

    @SneakyThrows
    protected HttpResponse targetPost(String url, Object request) {
        HttpPost post = new HttpPost(
                url
        );
        post.setEntity(new StringEntity(objectMapper.writeValueAsString(request)));
        post.addHeader("Accept", "application/json");
        post.addHeader("Content-Type", "application/json");

        httpClient = new DefaultHttpClient();
        HttpResponse response =  httpClient.execute(post);
        httpClient.getConnectionManager().shutdown();
        return response;
    }

    @SneakyThrows
    protected String readJsonData(HttpResponse response) {
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }
}
