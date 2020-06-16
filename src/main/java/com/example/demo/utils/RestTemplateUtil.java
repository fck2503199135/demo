package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 调用接口类
 */
@Component
public class RestTemplateUtil {

    private static RestTemplateUtil restTemplateUtil;

    @PostConstruct
    public void init() {
        restTemplateUtil = this;

    }

    private static final RestTemplate INSTANCE = new RestTemplate();

    private static RestTemplate getInstance() {
        return RestTemplateUtil.INSTANCE;
    }

    public static String get(String url) {
        ResponseEntity<String> entity = RestTemplateUtil.getInstance().
                getForEntity(url, String.class, new Object[]{});
        return entity.getBody();
    }

    public static <T> String post(String url, T data) throws Exception {
        //复杂构造函数的使用
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置超时
        requestFactory.setConnectTimeout(300000);
        requestFactory.setReadTimeout(300000);


        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept-Charset", MediaType.APPLICATION_JSON.toString());

        HttpEntity<T> object = new HttpEntity<>(data, headers);
        //利用复杂构造器可以实现超时设置，内部实际实现为 HttpClient
        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        restTemplate.setRequestFactory(requestFactory);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, object, String.class);
        return responseEntity.getBody();
    }

    /**
     * @param url
     * @param data
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> String postForm(String url, T data) throws Exception {
        //复杂构造函数的使用
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置超时
        requestFactory.setConnectTimeout(300000);
        requestFactory.setReadTimeout(300000);


        HttpEntity<T> object = new HttpEntity<>(data);
        //利用复杂构造器可以实现超时设置，内部实际实现为 HttpClient
        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        restTemplate.setRequestFactory(requestFactory);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, object, String.class);
        return new String(responseEntity.getBody().getBytes("utf-8"), "utf-8");
    }


    public static void main(String[] args) {

        String data = "{\"data\":\"123\"}";
        JSONObject jsonObject = JSON.parseObject(data);
        try {
            String s = post("URL", jsonObject);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}