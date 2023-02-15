package com.ddcode.controller;

import com.ddcode.dao.UserDao;
import com.ddcode.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserDao userDao;


    @GetMapping("/getAll")
    public Flux<User> getAll(){
        return userDao.findAll();
    }

    /**
     * 使用 SSE的方式一条一条获取
     * @return
     */
    @GetMapping(value = "/stream/getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getAllStream(){
        return userDao.findAll();
    }

    /**
     * 新增
     * @param user
     * @return
     */
    @RequestMapping("/insert")
    public Mono<User> insert(@RequestBody User user){
        log.info("insert user {}", user);
        return userDao.insert(user);
    }

    /**
     * 更新操作
     * @param user
     * @return
     */
    @RequestMapping("/save")
    public Mono<User> save(@RequestBody User user){
        log.info("save user {}", user);
        return userDao.save(user);
    }

    /**
     * 更新操作
     * 需求：如果有id对应的值值就更新，同时返回更新后的值
     * 如果没有就返回404
     *
     * @param id
     * @param user
     * @return
     */
    @RequestMapping("/updateById/{id}")
    public Mono<ResponseEntity<User>> updateById(@PathVariable("id") String id , @RequestBody User user){
        return userDao.findById(id)
                .flatMap(u -> {
                    u.setAge(user.getAge());
                    u.setName(user.getName());
                    log.info("id {}, 执行更新操作" , id);
                    return userDao.save(u);
                })
                .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * 查询操作
     * 需求：如果有id对应的值值就返回
     * 如果没有就返回404
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById/{id}")
    public Mono<ResponseEntity<User>> findById(@PathVariable("id") String id){
        return userDao.findById(id)
                .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 删除操作
     * 需求: 如果有该值，删除成功返回status=200
     * 如果没有该值，返回status=404
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id){
        return userDao.findById(id)
                //这里注意map和flatmap使用上的区别
                //当你需要操作数据的时候，并需要返回一个mono，这个时候使用flatmap
                //如果不需要操作数据，只是转化数据，使用map即可
                .flatMap(user -> userDao.delete(user))
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));

    }
}
