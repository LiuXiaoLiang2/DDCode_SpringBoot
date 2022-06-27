package com.ddcode.sharding.controller;

import com.ddcode.sharding.mapper.TbDeviceInfoMapper;
import com.ddcode.sharding.mapper.TbDeviceMapper;
import com.ddcode.sharding.mapper.TbDeviceTypeMapper;
import com.ddcode.sharding.po.TbDevice;
import com.ddcode.sharding.po.TbDeviceInfo;
import com.ddcode.sharding.po.TbDeviceType;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/tbDevice")
public class TbDeviceController {

    @Resource
    private TbDeviceMapper tbDeviceMapper;

    @Resource
    private TbDeviceInfoMapper tbDeviceInfoMapper;

    @RequestMapping("/save")
    public String save(){
        for (int i = 0; i < 10; i++) {
            TbDevice device = new TbDevice();
            device.setDeviceId((long) i);
            device.setDeviceType(i);
            tbDeviceMapper.insert(device);
        }
        return "ok";
    }

    @RequestMapping("/savePlus")
    public String savePlus(){
        List<String> list = Arrays.asList("iphone", "HTC", "HUAWEI", "XIAOMI", "SANXING");
        for (int i = 1; i < 11; i++) {
            TbDevice device = new TbDevice();
            device.setDeviceId((long) i);
            device.setDeviceType(i);
            tbDeviceMapper.insert(device);

            TbDeviceInfo tbDeviceInfo = new TbDeviceInfo();
            tbDeviceInfo.setDeviceId((long) i);
            tbDeviceInfo.setDeviceInfo(list.get(i % list.size()));
            tbDeviceInfoMapper.insert(tbDeviceInfo);
        }
        return "ok";
    }


    @RequestMapping("/select")
    public List<TbDevice> select(Long deviceId){
        return tbDeviceMapper.selectByDeviceId(deviceId);
    }

    @RequestMapping("/selectIn")
    public List<TbDevice> selectIn(){
        return tbDeviceMapper.selectIn();
    }


    @RequestMapping("/selectBetween")
    public List<TbDevice> selectBetween(Long deviceId){
        return tbDeviceMapper.selectBetweenByDeviceId(deviceId.intValue());
    }

    @RequestMapping("/selectBetweenPlus")
    public List<TbDevice> selectBetweenPlus(Integer start, Integer end){
        return tbDeviceMapper.selectBetweenByDeviceIdPlus(start, end);
    }


    @RequestMapping("/selectBetweenDeviceIdAndDeviceType")
    public List<TbDevice> selectBetweenDeviceIdAndDeviceType(Long deviceType, Integer deviceIdStart, Integer deviceIdEnd){
        return tbDeviceMapper.selectBetweenDeviceIdAndDeviceType(deviceType, deviceIdStart, deviceIdEnd);
    }

    @RequestMapping("/selectDeviceIdAndDeviceType")
    public List<TbDevice> selectDeviceIdAndDeviceType(Long deviceType, Long deviceId){
        return tbDeviceMapper.selectDeviceIdAndDeviceType(deviceType, deviceId);
    }

    @RequestMapping("/queryByHint")
    public List<TbDevice> queryByHint(){
        HintManager hintManager = HintManager.getInstance();
        //指定强制路由的数据库和表
        hintManager.addDatabaseShardingValue("tb_device",1L);
        hintManager.addTableShardingValue("tb_device",0L);
        List<TbDevice> tbDevices = tbDeviceMapper.selectList(null);
        hintManager.close();
        return tbDevices;
    }


    @RequestMapping("/tbDeviceInfoList")
    public List<TbDeviceInfo> tbDeviceInfoList(){
        return tbDeviceInfoMapper.tbDeviceInfoList();
    }

    @Resource
    private TbDeviceTypeMapper tbDeviceTypeMapper;

    @RequestMapping("/saveType")
    public String saveType(){
        TbDeviceType deviceType1 = new TbDeviceType();
        deviceType1.setTypeName("⼈脸考勤");
        tbDeviceTypeMapper.insert(deviceType1);


        TbDeviceType deviceType2 = new TbDeviceType();
        deviceType2.setTypeName("⼈脸通道");
        tbDeviceTypeMapper.insert(deviceType2);
        return "ok";
    }

    @RequestMapping("/selectType")
    public List<TbDeviceType> selectType(){
        return tbDeviceTypeMapper.selectList(null);
    }

}
