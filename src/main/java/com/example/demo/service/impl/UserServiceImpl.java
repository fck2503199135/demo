package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.UserPojo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户
 *
 * @author fan
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserPojo getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public List<UserPojo> getUserInfo(Integer storeId) {
        return userMapper.getUserInfo(storeId);
    }
}
