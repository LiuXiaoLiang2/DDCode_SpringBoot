package com.ddcode.mongo.service;

import com.alibaba.fastjson.JSON;
import com.ddcode.mongo.dao.CommentRepository;
import com.ddcode.mongo.po.Comment;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentService {

    //注入
    @Resource
    private CommentRepository commentRepository;

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 保存记录
     * @param comment
     */
    public void save(Comment comment){
        //insert操作
        //.insert: 若新增数据的主键已经存在，则会抛 org.springframework.dao.Duplicate KeyException 异常提示主键重复，不保存当前数据。
        Comment save = commentRepository.insert(comment);
        String id = save.getId();
        log.info("保存后的id {}", id);
    }

    /**
     * 更新评论
     * @param comment
     */
    public void update(Comment comment){
        //save: 若新增数据的主键已经存在，则会对当前已经存在的数据进行修改操作
        commentRepository.save(comment);
    }

    /**
     * 删除评论
     * @param id
     */
    public void deleteById(String id){
        commentRepository.deleteById(id);
    }

    /**
     * 查询所有
     */
    public void findAll(){
        List<Comment> all = commentRepository.findAll();
        log.info("查询所有 {}", all);
    }

    /**
     * 根据id查询
     * @param id
     */
    public void findById(String id){
        Optional<Comment> commentOptional = commentRepository.findById(id);
        Comment comment = commentOptional.get();
        log.info("根据id查询 {}",comment);
    }

    /**
     * 分页查询
     */
    public void page(String parentId, Integer currentPage, Integer pageSize){
        Page<Comment> commentPage = commentRepository.findByParentId(parentId, PageRequest.of(currentPage - 1, pageSize));
        log.info("分页查询 {}", JSON.toJSONString(commentPage));
    }

    /**
     * 增加点赞
     */
    public void addLikeNum(String id){
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("likeNum");
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Comment.class);
        log.info("增加点赞 {}" , JSON.toJSONString(updateResult));
    }

}
