package com.example.demo.mapper;

import com.example.demo.pojo.UserPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户
 *
 * @author fan
 */
public interface UserMapper {

    /**
     * 获取用户信息
     *
     * @param name
     * @return
     */
    UserPojo getUserByName(@Param("name") String name);


    List<UserPojo> getUserInfo(Integer storeId);
}
