package com.example.demo.controller;

import com.example.demo.common.RespBean;
import com.example.demo.pojo.UserPojo;
import com.example.demo.service.UserService;
import com.example.demo.utils.HtmlToImage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @ApiOperation("shiro登录")
    @PostMapping(value = "loginByShiro")
    @ResponseBody
    private RespBean loginByShiro(@RequestBody UserPojo user) {
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
     * 用户登录
     *
     * @author fan
     */
    @ApiOperation("用户登录")
    @PostMapping(value = "login")
    @ResponseBody
    private RespBean login(@RequestBody UserPojo user) {
        log.info("用户登录 user==>{}", user);
        if (StringUtils.isEmpty(user.getUserName())) {
            return RespBean.error("用户名为空");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return RespBean.error("密码为空");
        }
        UserPojo userPojo = userService.getUserByName(user.getUserName());
        if (userPojo == null) {
            return RespBean.error("用户名不存在");
        }
        // 登录校验
        int loginInfo = userService.login(user.getUserName(), user.getPassword());
        if (loginInfo > 0) {
            // 生成token
            String token = UUID.randomUUID().toString().replaceAll("-", "");
 /*           cacheService.h5TokenCache(username, token);

            PubUserAccountDo pubUser = pubUserAccountDoMapper.selectByLoginId(username);
            // 将用户信息保存到ehcache中
            UserAccountBean userInfo = new UserAccountBean();
            userInfo.setLocal(setLocale(lang));
            userInfo.setAccountId(pubUser.getAccountId());
            // logonId也就是用户登录时输入的用户名。比如shelftest01
            userInfo.setLogonId(username);
            // createdBy也就是该用户在pub_user_account表中的account_id
            userInfo.setCreatedBy(pubUser.getAccountId().toString());
            // user_id即公司id companyId
            userInfo.setCompanyId(pubUser.getUserId());
            cacheService.userAccountCache(username, userInfo);*/

            return RespBean.ok("用户登录成功", token);
        } else {
            return RespBean.error("用户登录失败");
        }
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


    /**
     * 调用HTML转为图片
     *
     * @return
     */
    @ApiOperation("调用HTML转为图片")
    @GetMapping(value = "changeHtmlToImage")
    @ResponseBody
    public RespBean changeHtmlToImage(String html, String name) {
        //TODO 调用HTML转为图片
        HtmlToImage.changeHtmlToImage(html, name);
        return RespBean.ok("success");
    }


}
