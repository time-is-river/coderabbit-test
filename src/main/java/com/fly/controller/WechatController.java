package com.fly.controller;

import com.fly.request.WechatLoginRequest;
import com.fly.service.SysUserService;
import com.fly.service.WechatService;
import com.fly.response.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @date 2021/5/20 14:41
 */
@RestController
@RequestMapping("/wechat")
public class WechatController {

    private final static Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private WechatService wechatService;

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/binding")
    public ResponseUtils binding(@RequestBody WechatLoginRequest request) {
        return wechatService.binding(request);
    }

    @PostMapping("/login")
    public ResponseUtils login(@RequestParam String code) {
        return wechatService.login(code);
    }

    @GetMapping("userInfo")
    public ResponseUtils userInfo(){
        return sysUserService.getUserInfo();
    }

    @GetMapping("/hello")
    public String hello() {
        logger.info("logback 日志测试=========");
        logger.error("logback error 日志测试");
        return "this is fly-manage-api response";
    }
}
