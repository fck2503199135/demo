package com.example.demo.service;

import com.example.demo.pojo.UserPojo;

import java.util.List;

/**
 * 用户
 *
 * @author fan
 */
public interface UserService {
    /**
     * 获取用户信息
     *
     * @param name
     * @return
     */
    UserPojo getUserByName(String name);

    /**
     * 获取用户
     *
     * @param storeId
     * @return
     */
    List<UserPojo> getUserInfo(Integer storeId);

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    int login(String userName, String password);
}
