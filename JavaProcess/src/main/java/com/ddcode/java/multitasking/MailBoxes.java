package com.ddcode.java.multitasking;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * 存放所有所有邮箱的地方
 */
@Slf4j(topic = "c.boxes")
public class MailBoxes {

    //使用线程安全的hashTable,用了存放
    private static Map<Integer, GuardedObject> boxes = new Hashtable<>();

    // 小格子的id
    private static int id = 1;

    // 产生唯一 id
    private static synchronized int generateId() {
        return id++;
    }

    //获取小格子对象
    public static GuardedObject getGuardedObject(int id) {
        return boxes.get(id);
    }

    //创建小格子对象,同时放到邮箱map中
    public static GuardedObject createGuardedObject() {
        GuardedObject go = new GuardedObject(generateId());
        boxes.put(go.getId(), go);
        return go;
    }

    //获取所有的邮箱ids
    public static Set<Integer> getIds() {
        return boxes.keySet();
    }
}
