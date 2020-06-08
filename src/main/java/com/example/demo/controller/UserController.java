package com.example.demo.controller;

import com.example.demo.common.RespBean;
import com.example.demo.pojo.UserPojo;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fan
 */
@Api(tags = "用户登录")
@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户登录
     *
     * @author fan
     */
    @ApiOperation("用户登录")
    @PostMapping(value = "login")
    @ResponseBody
    private RespBean login(@RequestBody UserPojo user) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        subject.login(usernamePasswordToken);
        return RespBean.ok("success");
    }

    /**
     * 查询用户
     *
     * @author fan
     */
    @ApiOperation("查询用户")
    @GetMapping(value = "getUserInfo")
    @ResponseBody
    private RespBean getUserInfo(Integer storeId, int pageNum, int pageSize) {
        // 分页
        PageHelper.startPage(pageNum, pageSize);
        List<UserPojo> userList = userService.getUserInfo(storeId);
        PageInfo<UserPojo> pageInfo = new PageInfo<>(userList);
        return RespBean.ok("success", pageInfo);
    }


}
