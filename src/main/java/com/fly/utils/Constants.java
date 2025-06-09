package com.fly.utils;

/**
 * @author
 *  常量工具类
 * @date 2021/6/21 15:35
 */
public interface Constants {

    interface DataStatus {

        /**
         * 有效状态
         */
        String VALID = "0";

        /**
         * 无效状态
         */
        String INVALID = "1";
    }

    interface AttachmentType {
        /**
         * 图片
         */
        String PICTURE = "0";
    }

    interface ApiResultCode {

        /**
         * 接口返回状态 成功：1
         */
        Integer SUCCESS = 1;
    }

    /**
     * 用户类型
     */
    interface  RoleType {

        /**
         * 管理员
         */
        String SUPER = "ROLE_SUPER";

        /**
         * 管理员
         */
        String ADMIN = "ROLE_ADMIN";

        /**
         * 普通用户
         */
        String COMMON_USER = "ROLE_USER";
    }


}
