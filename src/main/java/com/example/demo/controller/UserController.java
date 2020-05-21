package com.example.demo.controller;

import com.example.demo.common.RespBean;
import com.example.demo.pojo.UserPojo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fan
 */
@Api(tags = "用户登录")
@Slf4j
@RestController
public class UserController {

    /**
     * 用户登录
     *
     * @author fan
     */
    @ApiOperation("用户登录")
    @PostMapping(value = "login")
    @ResponseBody
    private RespBean login(UserPojo user) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        subject.login(usernamePasswordToken);
        return RespBean.ok("success");
    }


}
