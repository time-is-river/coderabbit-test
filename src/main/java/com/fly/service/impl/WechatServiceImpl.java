package com.fly.service.impl;

import com.fly.component.WechatApi;
import com.fly.config.security.SysUserDetail;
import com.fly.config.security.service.UserDetailServiceImpl;
import com.fly.config.security.utils.JWTTokenUtil;
import com.fly.entity.SysUser;
import com.fly.entity.wechat.Code2SessionResponse;
import com.fly.request.WechatLoginRequest;
import com.fly.response.ResultCode;
import com.fly.service.SysUserService;
import com.fly.service.WechatService;
import com.fly.response.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2021/5/21 10:05
 */
@Slf4j
@Service
/*@Transactional*/
public class WechatServiceImpl implements WechatService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private WechatApi wechatApi;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Binds a platform user account to a WeChat account using the provided login request.
     *
     * The method verifies the user's credentials, exchanges the WeChat code for an openId and sessionKey,
     * and associates the user's account with the WeChat openId. If the openId is already bound to another user,
     * it unbinds the previous association before binding the current user. On success, returns a JWT token for authentication.
     *
     * @param request the login request containing username, password, WeChat code, and nickname
     * @return a response containing the result code, message, and JWT token if successful; otherwise, an error response
     */
    @Override
    public ResponseUtils binding(WechatLoginRequest request) {
        SysUserDetail userDetail;
        try{
            String userName = request.getUsername();
            Code2SessionResponse code2SessionResponse = wechatApi.code2Session(request.getCode());
            if (code2SessionResponse != null && code2SessionResponse.getOpenId() != null) {
                userDetail = (SysUserDetail)userDetailService.loadUserByUsername(userName);
                if(!passwordEncoder.matches(request.getPassword(), userDetail.getPassword())) {
                    log.info("用户:{} 密码输入错误 errorPassword:{}", userName, request.getPassword());
                    return ResponseUtils.response(ResultCode.USER_PASSWORD_ERROR.getCode(), "密码错误", null);
                }
                //获取 登录成功的token
                String token = JWTTokenUtil.createAccessToken(userDetail);
                //获取微信用户的 openId sessionKey
                String openId = code2SessionResponse.getOpenId();
                String sessionKey = code2SessionResponse.getSessionKey();
                SysUser user = new SysUser();
                BeanUtils.copyProperties(userDetail, user);
                String oldOpenId = userDetail.getWechatOpenId();
                if (StringUtils.isNotEmpty(oldOpenId)) {
                    if (!openId.equals(oldOpenId)) {
                        //openId已存在且与当前微信用户不对应 则更换数据库openId为当前用户
                        user.setWechatOpenId(code2SessionResponse.getOpenId());
                        user.setWechatNickname(request.getNickname());
                        sysUserService.updateById(user);
                        log.info("用户{} 的原openId:{} 更换为:{}", userName, oldOpenId, openId);
                    }
                } else {
                    //账号初次绑定微信用户 存储 openId

                    //校验此openId是否绑定了其他用户 若绑定了则解除绑定
                    SysUser userBindOpenId = sysUserService.selectUserByOpenId(openId);
                    if (userBindOpenId != null) {
                        userBindOpenId.setWechatOpenId("");
                        userBindOpenId.setWechatNickname("");
                        sysUserService.updateById(userBindOpenId);
                        log.info("用户:{} 解除微信绑定 openId:{} wechatNickName:{}", userBindOpenId.getUsername(), openId, request.getNickname());
                    }
                    user.setWechatOpenId(openId);
                    user.setWechatNickname(request.getNickname());
                    sysUserService.updateById(user);
                    log.info("用户:{} 初次绑定微信openId:{}", userName, openId);
                }
                //redis存储用户信息，微信 openId、sessionKey
                JWTTokenUtil.setTokenInfo(token, userDetail.getUsername(), openId, sessionKey);
                log.info("用户:{} 登录成功。openId:{}", userDetail.getUsername(), openId);
                return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "登录成功了", token);
            } else {
                return ResponseUtils.response(420, code2SessionResponse.getErrMsg(), null);
            }
        } catch (UsernameNotFoundException e) {
            return ResponseUtils.response(302, "用户名不存在", null);
        } catch (Exception e) {
            return ResponseUtils.response(302, e.getMessage(), null);
        } finally {
            log.info("用户:{} 登录失败。", request.getUsername());
        }
    }

    /****
     * Authenticates a user via WeChat by exchanging a WeChat code for an openId and logging in the corresponding platform user.
     *
     * If the openId is bound to a platform user, generates and returns a JWT access token. If the openId is not bound, returns a response indicating the need to bind an account. Returns an error response if the WeChat API request fails or openId is missing.
     *
     * @param code the WeChat login code provided by the client
     * @return a response containing the login result and, if successful, a JWT access token
     */
    @Override
    public ResponseUtils login(String code) {
        Code2SessionResponse code2SessionResponse = wechatApi.code2Session(code);
        String openId = code2SessionResponse.getOpenId();
        if (StringUtils.isNotEmpty(openId)) {
            // String openId = code2SessionResponse.getOpenId();
            // 根据openId 获取绑定的用户信息
            SysUser user = sysUserService.selectUserByOpenId(openId);
            if (user == null) {
                log.info("当前微信用户未绑定平台账号");
                return ResponseUtils.response(ResultCode.USER_ACCOUNT_NOT_BINDING.getCode(), "未绑定平台用户,请绑定!", null);
            } else {
                //更新token 并返回
                SysUserDetail userDetail = (SysUserDetail)userDetailService.loadUserByUsername(user.getUsername());
                //获取 登录成功的token
                String token = JWTTokenUtil.createAccessToken(userDetail);
                //redis存储用户信息，微信 openId、sessionKey
                JWTTokenUtil.setTokenInfo(token, userDetail.getUsername(), openId, code2SessionResponse.getSessionKey());
                log.info("用户:{} 通过微信登录成功。openId:{}", user.getUsername(), openId);
                return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "登录成功了", token);
            }
        }
        log.info("请求微信接口异常 code:{}", code);
        return ResponseUtils.response(ResultCode.REQUEST_WECHAT_API_ERROR.getCode(), "请求微信接口异常", null);
    }
}
