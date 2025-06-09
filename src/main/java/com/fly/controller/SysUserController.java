package com.fly.controller;


import com.fly.request.UserLoginRequest;
import com.fly.request.UserPasswordRequest;
import com.fly.service.SysUserService;
import com.fly.response.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author flyManage
 * @since 2021-04-22
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public ResponseUtils login(@RequestBody UserLoginRequest request) {
        return sysUserService.login(request);
    }

    @PostMapping("/modifyPassword")
    public ResponseUtils modifyPassword(@RequestBody UserPasswordRequest request) {
        return sysUserService.modifyPassword(request);
    }

    @GetMapping("userInfo")
    public ResponseUtils userInfo(String userName){
        return sysUserService.getUserInfo();
    }

    @GetMapping("user")
    public String userInfo(){
        return new ResponseUtils(200, "处理成功",null).toString();
    }

    @GetMapping("home")
    public String home(){
        return new ResponseUtils(200, "home页面-处理成功",null).toString();
    }



}

