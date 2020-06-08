package com.example.demo.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @author fan
 */
@Data
@ToString
public class UserPojo {
    private String id;
    private String userName;
    private String password;
    private Integer storeId;
    /**
     * 用户对应的角色集合
     */
    private Set<RolePojo> roles;
}
