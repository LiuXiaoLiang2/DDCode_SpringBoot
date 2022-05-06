## 1、入门：security-quickstart

1-1、添加依赖

```aidl
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

1-2、自定义登录页面的账户名密码

```aidl
# 使用自定义的用户密码
spring.security.user.name=root
spring.security.user.password=123
```

## 2、自定义资源权限规则：security-custom-auth
默认情况下所有的请求都会走security

目的：就是定义哪些请求路径走security，哪些不走

编写配置类
```aidl
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
                .mvcMatchers("/hello").authenticated()// 哪些资源拦截, 也可以不写拦截, 直接使用anyRequest
                .anyRequest().authenticated()
                .and()// 返回的就是 http, 为了后面能够点formLogin
                .formLogin();//form表单认证
    }

}

```

