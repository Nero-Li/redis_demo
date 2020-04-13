package com.lyming.Jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName JedisClientDemo
 * @Description Sentinel哨兵
 * @Author lyming
 * @Date 2020/4/9 10:41 上午
 **/
public class JedisClientDemo {

    public static void main(String[] args) {
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        HostAndPort hostAndPort1 = new HostAndPort("192.168.2.201",7000);
        HostAndPort hostAndPort2 = new HostAndPort("192.168.2.201",7001);
        hostAndPorts.add(hostAndPort1);
        hostAndPorts.add(hostAndPort2);
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
        jedisCluster.set("lyming","hello");
    }
}
