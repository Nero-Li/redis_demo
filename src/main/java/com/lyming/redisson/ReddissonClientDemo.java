package com.lyming.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @ClassName ReddissonClientDemo
 * @Description
 * @Author lyming
 * @Date 2020/4/11 2:11 下午
 **/
public class ReddissonClientDemo {
    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers().setScanInterval(2000)
                .addNodeAddress("redis://192.168.2.201:7000", "redis://192.168.2.201:7001");
        RedissonClient redissonClient = Redisson.create(config);
        //分布式锁
//        redissonClient.getLock();
        //操作字符串
        redissonClient.getBucket("lyming").set("hello");
        //map集合
//        redissonClient.getMap()
        //zset
//        redissonClient.getSortedSet()
        //set
//        redissonClient.getSet()
        //list
//        redissonClient.getList()
    }

}
