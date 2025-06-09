package com.fly.config.security;

import com.fly.config.security.filter.JWTAuthenticationFilter;
import com.fly.config.security.handler.*;
import com.fly.config.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import javax.annotation.Resource;

/**
 * @author
 * @date 2021/4/23 14:14
 * Security授权配置文件
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法权限注解
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailServiceImpl userDetailService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserNotLoginHandler userNotLoginHandler;

    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;

    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;

    @Autowired
    private MyInvalidSessionStrategy myInvalidSessionStrategy;

    /**
     * 用户被迫下线
     */
    @Autowired
    private MyExpiredSessionStrategy myExpiredSessionStrategy;

    /**
     * 用户权限注解
     */
    @Autowired
    private UserPermissionEvaluator userPermissionEvaluator;

    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(userPermissionEvaluator);
        return handler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
      /*  //开启httpBasic 认证
        http.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();//任何请求都必须认证成功*/

        //所以请求都要求认证，所有的请求都会被拦截
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/login", "/login.html", "/sysUser/login", "/wechat/login","/wechat/binding","/wechat/hello").permitAll()
                /*.antMatchers("/users","roles","others")
                // hasAnyAuthority 可写多个权限 必须是 ROLE_ 格式
                .hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/menus")
                .hasAnyAuthority("ROLE_USER")*/
                /*.antMatchers("/menus","/others")
                // hasAnyRole 直接写角色
                .hasAnyRole("admin")*/
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                //.and().httpBasic().authenticationEntryPoint(userNotLoginHandler)//配置未登录处理类
                .logout().permitAll()
                .logoutSuccessHandler(userLogoutSuccessHandler)//用户登出成功 处理
                .and()
                //设置登录页面
                .formLogin().loginPage("/login.html")
                //设置表单的action
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(userLoginSuccessHandler)
                .failureHandler(userLoginFailureHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(userNotLoginHandler)//未登录用户访问未授权路径 处理
                .accessDeniedHandler(myAccessDeniedHandler)//权限不足处理
                .and().cors() //开启跨域
                .and().csrf().disable() //禁用跨站请求伪造防护
                //登录成功之后的默认跳转路径
                /*.defaultSuccessUrl("/sysUser/home")*/
                //todo 配置spring security 不使用session  通过JWT方式 处理
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /*.sessionManagement().invalidSessionStrategy(myInvalidSessionStrategy)
                .maximumSessions(1)//最大允许登录数量
                .maxSessionsPreventsLogin(false)//是否限制另一个账号登录
                .expiredSessionStrategy(myExpiredSessionStrategy)//被挤下线的处理方式*/
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //禁用token
        http.headers().cacheControl(); //禁用缓存
        //http.addFilter(new JWTAuthenticationFilter(authenticationManager())); //// 添加JWT过滤器
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
        /*auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder.encode("123"))
                .roles("user")
                .and()
                .withUser("admin")
                .password(passwordEncoder.encode("123"))
                .roles("admin")
                .and()
                .passwordEncoder(passwordEncoder);//配置auth的加密方式为passwordEncoder*/
    }
}
