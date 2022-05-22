package com.ddcode.mongo.po;

import lombok.Builder;
import lombok.Data;
import org.omg.CORBA.UNKNOWN;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 对应mongodb实体类
 * 把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档
 *
 */
@Data
@Builder
//@Document(collection="mongodb 对应 collection 名")
@Document(collection = "comment")
//复合索引, 同时设置唯一索引
//@CompoundIndex( def = "{'userid': 1, 'nickname': -1}", unique = true)
public class Comment {

    // //主键标识，该属性的值会自动对应mongodb的主键字段"_id"，如果该属性名就叫“id”,则该注解可以省略，否则必须写
    @Id
    private String id;

    ////该属性对应mongodb的字段的名字，如果一致，则无需该注解
    @Field("content")
    private String content;

    private Date publishTime;//发布日期

    //设置索引, 并且是唯一索引
    @Indexed(unique = true)
    private String userId;//发布人ID


    private String nickname;//昵称
    private LocalDateTime createDateTime;//评论的日期时间
    private Integer likeNum;//点赞数
    private Integer replyNum;//回复数
    private String state;//状态
    private String parentId;//上级ID
    private String articleId;
}
