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