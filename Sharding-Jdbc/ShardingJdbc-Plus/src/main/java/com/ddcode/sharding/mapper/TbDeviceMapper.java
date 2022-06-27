package com.ddcode.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddcode.sharding.po.TbDevice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbDeviceMapper extends BaseMapper<TbDevice> {

    @Select("select * from tb_device where device_id = #{deviceId}")
    List<TbDevice> selectByDeviceId(@Param("deviceId") Long deviceId);

    @Select("select * from tb_device where device_id in (1,3)")
    List<TbDevice> selectIn();

    @Select("select * from tb_device where device_id between 0 and #{deviceId}")
    List<TbDevice> selectBetweenByDeviceId(@Param("deviceId") Integer deviceId);

    @Select("select * from tb_device where device_id between #{start} and #{end}")
    List<TbDevice> selectBetweenByDeviceIdPlus(@Param("start") Integer start, @Param("end") Integer end);

    @Select("select * from tb_device where device_id between #{deviceIdStart} and #{deviceIdEnd} and device_type = #{deviceType}")
    List<TbDevice> selectBetweenDeviceIdAndDeviceType(@Param("deviceType") Long deviceType, @Param("deviceIdStart") Integer deviceIdStart,  @Param("deviceIdEnd") Integer deviceIdEnd);

    @Select("select * from tb_device where device_id = #{deviceId} and device_type = #{deviceType}")
    List<TbDevice> selectDeviceIdAndDeviceType(@Param("deviceType") Long deviceType, @Param("deviceId") Long deviceId);
}
