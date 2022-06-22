package com.ddcode.sharding.controller;

import com.ddcode.sharding.mapper.KsdUserMapper;
import com.ddcode.sharding.po.KsdUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        KsdUser ksdUser = KsdUser.builder().nickname("张三" + nextInt).password(nextInt + "").sex(nextInt).birthday(nextInt + "").build();
        ksdUserMapper.insert(ksdUser);
        return "ok";
    }

    @RequestMapping("/list")
    public List<KsdUser> list(){
        return ksdUserMapper.selectList(null);
    }

    @RequestMapping("/list/master")
    public List<KsdUser> listMaster(){
        //强制读主库
        List<KsdUser> ksdUsers;
        HintManager hintManager = null;
        try {
            hintManager = HintManager.getInstance();
            hintManager.setMasterRouteOnly();
            ksdUsers = ksdUserMapper.selectList(null);
        } finally {
            if(hintManager != null){
                hintManager.close();
            }
        }

        return ksdUsers;
    }


    @RequestMapping("/save/sharding")
    public String save(@RequestParam("sex") Integer sex, @RequestParam("age") Integer age){
        int nextInt = new Random().nextInt(20);
        KsdUser ksdUser = KsdUser.builder().nickname("张三" + nextInt).password(nextInt + "").sex(sex).birthday(nextInt + "").age(age).build();
        ksdUserMapper.insert(ksdUser);
        return "ok";
    }

    @RequestMapping("/save/sharding/birthday")
    public String save(@RequestParam("sex") Integer sex, @RequestParam("birthday") String birthday){
        int nextInt = new Random().nextInt(20);
        KsdUser ksdUser = KsdUser.builder().nickname("张三" + nextInt).password(nextInt + "").sex(sex).birthday(birthday).age(sex).build();
        ksdUserMapper.insert(ksdUser);
        return "ok";
    }

}
