package com.example.demo.service;

import com.example.demo.pojo.UserPojo;

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
}
