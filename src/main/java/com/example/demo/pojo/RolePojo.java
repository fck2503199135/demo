package com.example.demo.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @author fan
 */
@Data
@ToString
public class RolePojo {
    private String id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<PermissionsPojo> permissions;
}
