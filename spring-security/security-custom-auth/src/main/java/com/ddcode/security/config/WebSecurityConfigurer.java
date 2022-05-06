package com.ddcode.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 自定义资源路径
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * # 说明
     * - permitAll() 代表放行该资源,该资源为公共资源 无需认证和授权可以直接访问
     * - anyRequest().authenticated() 代表所有请求,必须认证之后才能访问
     * - formLogin() 代表开启表单认证
     * ## 注意: 放行资源必须放在所有认证请求之前!
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()//开启权限管理
                .mvcMatchers("/index").permitAll()// 哪些资源放行
                .mvcMatchers("/login.html").permitAll() // 释放的也是请求，也是通过controller访问到页面的, 这里释放的是请求地址, loginController中的请求地址
                .mvcMatchers("/hello").authenticated()// 哪些资源拦截, 也可以不写拦截, 直接使用anyRequest
                .anyRequest().authenticated()
                .and()// 返回的就是 http, 为了后面能够点formLogin
                .formLogin() //form表单认证
                .loginPage("/login.html")// 替换认证受限登录页面为我们自己的页面, 这里也是请求地址
                .loginProcessingUrl("/doLogin")//一旦使用了自定义页面,必须要指定自定义页面请求登录要访问的地址,其实就是将自定义页面提交的用户名和密码给哪个接口地址，要通知给springsecurity, 方便于springsecurity完成认证操作, 这个接口地址不需要写, 因为security只要捕获这个地址获取用户名和密码就可以完成认证
                .usernameParameter("uname")
                .passwordParameter("passwd")
                //.successForwardUrl("/successForwardUrl.html") //认证称成功后，跳转到指定的路径, 注意路径不会发生变化
                .defaultSuccessUrl("/defaultSuccessUrl.html", true);
    }

}
