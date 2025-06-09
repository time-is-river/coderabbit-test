package com.fly.response;

/**
 * @author
 * @date 2021/5/30 14:12
 */
public enum ResultCode implements CustomizeResultCode{

    /**
     * 处理成功
     */
    SUCCESS(200, "成功"),

    /**
     * 处理失败
     */
    FAIL(500, "失败"),

    /**
     * 登录错误
     */
    USER_NOT_LOGIN(401, "用户未登录"),
    USER_PASSWORD_ERROR(2002, "密码不正确"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_BINDING(2007, "微信用户未绑定账号"),

    /**
     * 请求外部接口异常
     */
    REQUEST_WECHAT_API_ERROR(4100, "请求微信API异常")
    ;

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
