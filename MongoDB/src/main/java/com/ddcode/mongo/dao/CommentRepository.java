package com.ddcode.mongo.dao;

import com.ddcode.mongo.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 直接继承MongoRepository
 * 第泛型的1个参数：Collection对应的实体
 * 泛型的第2个参数：是主键的类型 String
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    /**
     * 分页查询，注意方法名一定是按照参数来定义的，不能随便写
     * @param parentId
     * @param pageable
     * @return
     */
    public Page<Comment> findByParentId(String parentId, Pageable pageable);
}
