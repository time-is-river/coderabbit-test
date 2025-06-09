package com.fly.entity.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取微信用户信息返回
 * @author
 * @date 2021/5/20 13:18
 */
@Data
public class Code2SessionResponse {
    @JsonProperty("openid")
    String openId;
    @JsonProperty("session_key")
    String  sessionKey;
    @JsonProperty("unionid")
    String  unionId;
    @JsonProperty("errcode")
    String  errCode;
    @JsonProperty("errmsg")
    String errMsg;
}
