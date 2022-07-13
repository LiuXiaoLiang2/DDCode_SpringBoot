package com.ddcode.es.controller;

import com.ddcode.es.dao.UserDao;
import com.ddcode.es.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/es/dao")
@Slf4j
public class UserDaoController {

    @Resource
    private UserDao userDao;

    /**
     * 添加用户
     * @return
     */
    @RequestMapping("addUser")
    public String addUser(){
        User user = User.builder().id("5").name("李六").sex("女").build();
        User save = userDao.save(user);
        log.info("addUser {}", save);
        return "ok";
    }

    /**
     * 更新操作【覆盖更新】
     * @return
     */
    @RequestMapping("updateUser")
    public String updateUser(){
        User user = User.builder().id("5").name("李六_update").build();
        User save = userDao.save(user);
        log.info("addUser {}", save);
        return "ok";
    }

    /**
     * 删除文档
     * @return
     */
    @RequestMapping("deleteUser")
    public String deleteUser(){
        User user = User.builder().id("4").build();
        userDao.delete(user);
        return "ok";
    }

    @RequestMapping("findById")
    public String findById(){
        Optional<User> optionalUser = userDao.findById("4");
        User user = optionalUser.get();
        log.info("user {}", user);
        return "ok";
    }

    @RequestMapping("findByName")
    public String findByName(){
       User byName = userDao.findByName("王五1");
       log.info("user {}", byName);
        return "ok";
    }

    @RequestMapping("findBySex")
    public String findBySex(){
        List<User> userList = userDao.findBySex("男");
        log.info("user {}", userList);
        return "ok";
    }


}
