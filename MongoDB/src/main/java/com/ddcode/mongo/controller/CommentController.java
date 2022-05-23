package com.ddcode.mongo.controller;

import com.ddcode.mongo.po.Comment;
import com.ddcode.mongo.service.CommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mongo")
public class CommentController {

    @Resource
    public CommentService commentService;

    @RequestMapping("/save")
    public String save(){

        Comment comment1 = Comment.builder()
                .id("1")
                .articleId("1")
                .content("好爱好爱你")
                .createDateTime(LocalDateTime.now())
                .likeNum(1000)
                .nickname("啦啦啦")
                .publishTime(new Date())
                .replyNum(10)
                .build();
        //comment2不设置id,使用mongo的
        Comment comment2 = Comment.builder()
                .articleId("2")
                .content("雨雨雨雨")
                .createDateTime(LocalDateTime.now())
                .likeNum(10)
                .nickname("哦哦哦")
                .publishTime(new Date())
                .replyNum(10)
                .parentId("1")
                .build();

        Comment comment3 = Comment.builder()
                .articleId("3")
                .content("鹅鹅鹅")
                .createDateTime(LocalDateTime.now())
                .likeNum(1)
                .nickname("www")
                .publishTime(new Date())
                .replyNum(10)
                .build();

        List<Comment> list = new ArrayList<>();
        Collections.addAll(list, comment1, comment2, comment3);
        //使用lambda调用
        list.stream().forEach(comment -> commentService.save(comment));
        return "ok";
    }

    @RequestMapping("/update")
    public String update(){
        Comment comment1 = Comment.builder()
                .id("1")
                .articleId("1")
                .build();
        commentService.update(comment1);
        return "ok";
    }


    @RequestMapping("/findAll")
    public String findAll(){
        commentService.findAll();
        return "ok";
    }

    @RequestMapping("/findById")
    public String findById(){
        commentService.findById("1");
        return "ok";
    }

    @RequestMapping("/deleteById")
    public String deleteById(){
        commentService.deleteById("1");
        return "ok";
    }

    @RequestMapping("/page")
    public String page(){
        commentService.page("1", 1, 3);
        return "ok";
    }

    @RequestMapping("/addLikeNum")
    public String addLikeNum(){
        commentService.addLikeNum("1");
        return "ok";
    }
}
