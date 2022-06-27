package com.ddcode.sharding.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TbDeviceInfo {

    //使用数据库自带的自增
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long deviceId;
    private String deviceInfo;
}
