package com.ddcode.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddcode.sharding.po.TbDevice;
import com.ddcode.sharding.po.TbDeviceInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbDeviceInfoMapper extends BaseMapper<TbDeviceInfo> {

    @Select("select t1.* from tb_device t0 LEFT JOIN tb_device_info t1 on t0.device_id = t1.device_id")
    List<TbDeviceInfo> tbDeviceInfoList();
}
