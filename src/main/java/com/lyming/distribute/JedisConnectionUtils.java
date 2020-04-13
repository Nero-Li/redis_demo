package com.lyming.distribute;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import sun.security.krb5.Config;

import java.sql.Statement;

/**
 * @ClassName JedisConnectionUtils
 * @Description TODO
 * @Author lyming
 * @Date 2020/4/11 2:49 下午
 **/
public class JedisConnectionUtils {
    private static JedisPool pool = null;
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        pool = new JedisPool(jedisPoolConfig, "192.2168.2.204", 6379);
    }
    public static Jedis getJedis(){
        return pool.getResource();
    }

}
