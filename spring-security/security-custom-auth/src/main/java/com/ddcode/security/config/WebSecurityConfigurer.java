package com.ddcode.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

/**
 * 自定义资源路径
 */
@Configuration
@Slf4j
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
                //.defaultSuccessUrl("/defaultSuccessUrl.html", true)
                .successHandler(new MyAuthenticationSuccessHandler()) // 前后端分离, 响应给前端json
                //.failureForwardUrl("/login.html") //失败跳转请求, 还是跳转到登录页面, 失败信息存储到 request 中
                //.failureUrl("/login.html")   //失败跳转请求, 还是重定向到登录页面, 失败信息存储到 session 中
                .failureHandler(new MyAuthenticationFailureHandler()) // 当登录失败,前后端分离, 响应给前端json
                .and()
                .logout()      //开启注销登录
                //.logoutUrl("/logout") //指定退出登录请求地址，默认是 GET 请求，路径为 `/logout` , 注意要想使用默认的, 这个地址必须是 logout, 同时必须是get请求
                .logoutRequestMatcher(
                     new OrRequestMatcher(
                             new AntPathRequestMatcher("/aa","POST"), // post 请求 路径 /aa ，也可以完成注销操作
                             new AntPathRequestMatcher("/logout","GET")
                     )
                )
                .invalidateHttpSession(true) //退出时是否是 session 失效，默认值为 true
                .clearAuthentication(true) //退出时是否清除认证信息，默认值为 true
                //.logoutSuccessUrl("/login.html") //退出登录时跳转地址
                .logoutSuccessHandler(new MyLogoutSuccessHandler())  // 前后端分离, 注销后 返回json
                .and().csrf().disable();//这里先关闭 CSRF
                ;
    }

    /**
     * 配置认证管理器, 也是 继承 WebSecurityConfigurerAdapter
     */

    /**
     * 全局配置
     * 传入的参数就是一个构造器
     * @param builder
     */
    @Autowired
    public void initialize(AuthenticationManagerBuilder builder) throws Exception {
        //builder..
        InMemoryUserDetailsManager inMemoryUserDetailsManager
                = new InMemoryUserDetailsManager();
        UserDetails u1 = User.withUsername("zhangsan")
                .password("{noop}111").roles("USER").build();
        inMemoryUserDetailsManager.createUser(u1);
        builder.userDetailsService(inMemoryUserDetailsManager);
        log.info("全局配置 builder {}", builder);
    }


    /**
     * 自定义的配置
     * 重写 WebSecurityConfigurerAdapter 的config方法
     * @param builder
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        log.info("自定义配置 builder {}", builder);
//    }
}
