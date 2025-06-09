package com.fly.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @date 2021/11/25 10:32
 */
public class Constant {
    public final static Map<String, String> RoleType = new HashMap<String, String>() {
        {
            put("ROLE_admin", "管理员");
            put("ROLE_user", "普通用户");
        }
    };
}
