package com.ddcode.sharding.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KsdUser {
    //使用数据库的主键自增
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String nickname;
    private String password;
    private String sex;
    private String birthday;

}
