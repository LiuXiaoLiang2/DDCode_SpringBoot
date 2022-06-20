package com.ddcode.java.currentHashMap;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j(topic = "c.map")
public class Demo_2_Map {
    public static void main(String[] args) {
        Map<String,String > map = new HashMap<>();
        map.put("name", "张安");
        map.put("age", "11");
        map.put("birthday", "2000-09-09");
        //返回entry对象的set集合，set集合不会重复
        Set<Map.Entry<String, String>> entries = map.entrySet();
        entries.stream().forEach((entry)->{
            log.info("entry_key {}, entry_value {} , entry {}", entry.getKey(), entry.getValue(), entry.toString());
        });
    }
}
