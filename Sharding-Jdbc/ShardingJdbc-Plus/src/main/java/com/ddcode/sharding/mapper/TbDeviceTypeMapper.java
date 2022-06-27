package com.ddcode.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddcode.sharding.po.TbDeviceInfo;
import com.ddcode.sharding.po.TbDeviceType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbDeviceTypeMapper extends BaseMapper<TbDeviceType> {

}
