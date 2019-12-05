package com.karmanno.payments.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Injector;
import com.karmanno.payments.Application;
import com.karmanno.payments.dao.AccountDao;
import com.karmanno.payments.dao.CurrencyDao;
import com.karmanno.payments.dao.UserDao;
import com.karmanno.payments.domain.*;
import com.karmanno.payments.dto.CreateAccountRequest;
import com.karmanno.payments.dto.CreateCurrencyRequest;
import com.karmanno.payments.dto.CreateUserRequest;
import com.karmanno.payments.service.AccountService;
import com.karmanno.payments.service.CurrencyPriceService;
import io.logz.guice.jersey.JerseyServer;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Map;

public abstract class IntegrationTest {
    private DefaultHttpClient httpClient;
    ObjectMapper objectMapper;
    private static JerseyServer jerseyServer;
    private static final String HOST = "http://localhost:9001/";

    @BeforeEach
    public void before() {
        httpClient = new DefaultHttpClient();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void after() {
        httpClient.getConnectionManager().shutdown();
        Application.dropAll(getInjector());
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
    protected HttpResponse targetPost(String path, Object request) {
        HttpPost post = new HttpPost(
                HOST + path
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
    protected HttpResponse targetPut(String path, Object request) {
        HttpPut put = new HttpPut(
                HOST + path
        );
        put.setEntity(new StringEntity(objectMapper.writeValueAsString(request)));
        put.addHeader("Accept", "application/json");
        put.addHeader("Content-Type", "application/json");

        httpClient = new DefaultHttpClient();
        HttpResponse response =  httpClient.execute(put);
        httpClient.getConnectionManager().shutdown();
        return response;
    }

    @SneakyThrows
    protected HttpResponse targetGet(String path, List<NameValuePair> queryParams) {
        HttpGet httpGet;
        if (queryParams != null) {
            httpGet = new HttpGet(
                    HOST + path + "?" + queryParams(queryParams)
            );
        } else {
            httpGet = new HttpGet(
                    HOST + path
            );
        }
        httpGet.addHeader("Accept", "application/json");

        httpClient = new DefaultHttpClient();
        HttpResponse response =  httpClient.execute(httpGet);
        httpClient.getConnectionManager().shutdown();
        return response;
    }

    private String queryParams(List<NameValuePair> queryParams) {
        return URLEncodedUtils.format(queryParams, "UTF-8");
    }

    @SneakyThrows
    protected HttpResponse targetGet(String path) {
        return targetGet(path, null);
    }

    @SneakyThrows
    protected String readJsonData(HttpResponse response) {
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    private static Injector getInjector() {
        return Application.injector;
    }

    @SneakyThrows
    protected User withUser(String username) {
        UserDao userDao = getInjector().getInstance(UserDao.class);
        return userDao.create(username);
    }

    @SneakyThrows
    protected Currency withCurrency(String fullName, String code, Integer units) {
        CurrencyDao currencyDao = getInjector().getInstance(CurrencyDao.class);
        return currencyDao.create(
                new Currency().setFullName(fullName).setCode(code).setMinorUnits(units)
        );
    }

    @SneakyThrows
    protected Account withAccount(Integer userId, String currencyCode) {
        AccountService accountService = getInjector().getInstance(AccountService.class);
        return accountService.create(userId, currencyCode);
    }

    @SneakyThrows
    protected CurrencyPrice withPrice(String codeFrom, String codeTo, String price) {
        CurrencyPriceService currencyPriceService = getInjector().getInstance(CurrencyPriceService.class);
        return currencyPriceService.create(codeFrom, codeTo, price);
    }

    protected Account getAccount(String number) {
        AccountDao accountDao = getInjector().getInstance(AccountDao.class);
        return accountDao.findByNumber(number);
    }
}
