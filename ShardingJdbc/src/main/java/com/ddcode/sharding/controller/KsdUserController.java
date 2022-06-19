package com.ddcode.sharding.controller;

import com.ddcode.sharding.mapper.KsdUserMapper;
import com.ddcode.sharding.po.KsdUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/ksd")
public class KsdUserController {

    @Resource
    private KsdUserMapper ksdUserMapper;

    @RequestMapping("/save")
    public String save(){
        int nextInt = new Random().nextInt(10);
        KsdUser ksdUser = KsdUser.builder().nickname("张三" + nextInt).password(nextInt + "").sex(nextInt + "").birthday(nextInt + "").build();
        ksdUserMapper.insert(ksdUser);
        return "ok";
    }

    @RequestMapping("/list")
    public List<KsdUser> list(){
        return ksdUserMapper.selectList(null);
    }

}
