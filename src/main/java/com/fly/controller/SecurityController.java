package com.fly.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author
 * @date 2021/4/27 11:29
 */
@Controller
public class SecurityController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('USER')")
    public String users() {
        return "users";
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String roles() {
        return "roles";
    }

    @GetMapping("/menus")
    @PreAuthorize("hasAnyRole('ADMIN','USER') and hasPermission('/user/auth', 'sys:auth:info')")
    public String menus() {
        return "menus";
    }

    @GetMapping("/others")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String others() {
        return "others";
    }
}
