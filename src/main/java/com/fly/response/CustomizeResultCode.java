package com.fly.response;

/**
 * @author
 * @date 2021/5/30 11:24
 */
interface CustomizeResultCode {

    /**
     * 获取请求状态码
     * @return
     */
    Integer getCode();

    /**
     * 获取状态对应描述信息
     * @return
     */
    String getMessage();
}
