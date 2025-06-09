package com.fly.config.security.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fly.config.JWTConfig;
import com.fly.config.security.SysUserDetail;
import com.fly.config.security.service.UserDetailServiceImpl;
import com.fly.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * JWT token 工具类
 * @author
 * @date 2021/5/18 15:50
 */
@Slf4j
public class JWTTokenUtil {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    private static JWTTokenUtil jwtTokenUtil;

    /**
     * 时间格式
     */
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostConstruct
    public void init() {
        jwtTokenUtil =this;
        jwtTokenUtil.userDetailService = this.userDetailService;
    }

    /**
     * 使用用户信息 创建token
     * @param userDetail
     * @return
     */
    public static String createAccessToken(SysUserDetail userDetail) {
        String token = Jwts.builder().setId(userDetail.getId().toString())
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date())
                .setIssuer("C3tones")
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))//过期时间
                .signWith(SignatureAlgorithm.HS256, JWTConfig.secret) //签名算法、秘钥
                .claim("authorities", JSON.toJSONString(userDetail.getAuthorities())) //自定义其他属性
                .compact();
        return JWTConfig.tokenPrefix + token;
    }

    /**
     * 解析 token 获取用户信息
     * @param token
     * @return
     */
    public static SysUserDetail parseAccessToken(String token) {
        SysUserDetail userDetail = null;
        if (StringUtils.isNotEmpty(token)) {
            try{
                token = token.substring(JWTConfig.tokenPrefix.length());//去掉JWT前缀
                Claims claims = Jwts.parser().setSigningKey(JWTConfig.secret).parseClaimsJws(token).getBody();//解析token
                userDetail = new SysUserDetail();
                userDetail.setId(Long.parseLong(claims.getId()));
                userDetail.setUsername(claims.getSubject());//获取用户信息
                Set<GrantedAuthority> authorities = new HashSet<>();
                String authority = claims.get("authorities").toString();
                if (StringUtils.isNotEmpty(authority)) {
                    List<Map<String, String>> authorityList = JSON.parseObject(authority,
                            new TypeReference<List<Map<String, String>>>() {
                            });
                    for (Map<String, String> role : authorityList) {
                        if (!role.isEmpty()) {
                            authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                        }
                    }
                }
                userDetail.setAuthorities(authorities);
            } catch (Exception e) {
                log.error("JWTTokenUtil 解析token 异常",e.getMessage());
            }
        }
        return userDetail;
    }

    /**
     * 刷新Token（暂时不主动刷新token 还没有兼容 微信的token 刷新策略）
     * @param oldToken
     * @return
     */
    public static String refreshAccessToken(String oldToken) {
        String username = JWTTokenUtil.getUserNameByToken(oldToken);
        SysUserDetail userDetail = (SysUserDetail)jwtTokenUtil.userDetailService.loadUserByUsername(username);
        return createAccessToken(userDetail);
    }

    /**
     * 保存Token信息到redis中
     * @param token
     * @param username
     */
    public static void setTokenInfo(String token, String username, String openId, String sessionKey) {
        if (StringUtils.isNotEmpty(token)) {
            //同一时段只可以有一个账号在线
            //实现逻辑：相同账号登录只保存最新生成的token 删除redis中之前存储token
            if (RedisUtils.hasKey(username)) {
                RedisUtils.delete(RedisUtils.getString(username));
                log.info("用户 {} 被迫下线", username);
            }
            token = token.substring(JWTConfig.tokenPrefix.length());
            Integer refreshTime = JWTConfig.expiration;
            LocalDateTime localDateTime = LocalDateTime.now();
            //将相关信息存储到 redis中
            RedisUtils.set(username, token);
            RedisUtils.hset(token, "username", username, refreshTime);
            RedisUtils.hset(token, "refreshTime", df.format(localDateTime.plus(refreshTime, ChronoUnit.MILLIS)), refreshTime);
            RedisUtils.hset(token, "expiration", df.format(localDateTime.plus(refreshTime, ChronoUnit.MILLIS)), refreshTime);
            if (StringUtils.isNotEmpty(openId) && StringUtils.isNotEmpty(sessionKey)) {
                //微信端登录 存储 openId sessionKey
                RedisUtils.hset(token, "openId", openId);
                RedisUtils.hset(token, "sessionKey", sessionKey);
            }
        }
    }

    /**
     * 删除redis中指定key
     * @param token
     */
    public static void deleteRedisToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            String tokenKey = token.substring(JWTConfig.tokenPrefix.length());
            log.info("用户 {} 的token 已从redis中移除", getUserNameByToken(token));
            RedisUtils.deleteKey(tokenKey);
        }
    }

    /**
     * 是否过期
     *
     * @param expiration 过期时间，字符串
     * @return 过期返回True，未过期返回false
     */
    public static boolean isExpiration(String expiration) {
        LocalDateTime expirationTime = LocalDateTime.parse(expiration, df);
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.compareTo(expirationTime) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否有效
     *
     * @param refreshTime 刷新时间，字符串
     * @return 有效返回True，无效返回false
     */
    public static boolean isValid(String refreshTime) {
        LocalDateTime validTime = LocalDateTime.parse(refreshTime, df);
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.compareTo(validTime) > 0) {
            return false;
        }
        return true;
    }

    /**
     * 检查Redis中是否存在Token
     *
     * @param token Token信息
     * @return
     */
    public static boolean hasToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JWTConfig.tokenPrefix.length());
            return RedisUtils.hasKey(token);
        }
        return false;
    }

    /**
     * 获取Redis中openId的值
     *
     * @param token Token信息
     * @return
     */
    public static String getOpenIdByToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JWTConfig.tokenPrefix.length());
            return  (RedisUtils.hget(token, "openId")==null?"":RedisUtils.hget(token, "openId").toString());
        }
        return "";
    }

    /**
     * 获取Redis中sessionKey的值
     *
     * @param token Token信息
     * @return
     */
    public static String getSessionKeyByToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JWTConfig.tokenPrefix.length());
            return  (RedisUtils.hget(token, "sessionKey")==null?"":RedisUtils.hget(token, "sessionKey").toString());
        }
        return "";
    }

    /**
     * 从Redis中获取过期时间
     *
     * @param token Token信息
     * @return 过期时间，字符串
     */
    public static String getExpirationByToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JWTConfig.tokenPrefix.length());
            return RedisUtils.hget(token, "expiration").toString();
        }
        return null;
    }

    /**
     * 从Redis中获取刷新时间
     *
     * @param token Token信息
     * @return 刷新时间，字符串
     */
    public static String getRefreshTimeByToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JWTConfig.tokenPrefix.length());
            return RedisUtils.hget(token, "refreshTime").toString();
        }
        return null;
    }


    /**
     * 从Redis中获取用户名
     *
     * @param token Token信息
     * @return
     */
    public static String getUserNameByToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JWTConfig.tokenPrefix.length());
            return RedisUtils.hget(token, "username").toString();
        }
        return null;
    }



}
