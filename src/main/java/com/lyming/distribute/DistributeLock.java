package com.lyming.distribute;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.UUID;

/**
 * @ClassName DistributeLock
 * @Description TODO
 * @Author lyming
 * @Date 2020/4/11 2:57 下午
 **/
public class DistributeLock {

    //获得锁

    /**
     * @param lockName       锁的名称
     * @param acquireTimeout 获得锁的超时时间
     * @param lockTimeout    锁的超时时间
     * @return
     */
    public String acquireLock(String lockName, long acquireTimeout, long lockTimeout) {
        //判断释放锁的时候是否是同一个持有锁的人
        String identifier = UUID.randomUUID().toString();
        //锁的名称
        String lockKey = "lock" + lockName;
        //转化锁的超时时间
        int lockExpire = (int) (lockTimeout / 1000);
        Jedis jedis = null;
        try {
            jedis = JedisConnectionUtils.getJedis();

            long end = System.currentTimeMillis() + acquireTimeout;
            //在超时时间内可以不断重试
            while (System.currentTimeMillis() < end) {
                try {
                    if (jedis.setnx(lockKey, identifier) == 1) {
                        //设置超时时间
                        jedis.expire(lockKey, lockExpire);
                        //获取锁
                        return identifier;
                    }
                    if (jedis.ttl(lockKey) == -1) {
                        jedis.expire(lockKey, lockExpire);
                    }
                    //立刻重试也没有意义,休眠0.1s
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            jedis.close();
        }
        return null;
    }

    //释放锁
    public boolean releaseLock(String lockName, String identifier) {
        System.out.println(lockName+"开始释放锁:"+identifier);
        String lockKey = "lock" + lockName;
        Jedis jedis = null;
        boolean isRelease = false;
        try {
            jedis = JedisConnectionUtils.getJedis();
            while (true) {
                jedis.watch(lockKey);
                //判断是否是同一把锁
                if (identifier.equals(jedis.get(lockKey))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(lockKey);
                    if (transaction.exec().isEmpty()) {
                        continue;
                    }
                    isRelease = true;
                }
                jedis.unwatch();
                break;
            }
        }finally {
            jedis.close();
        }
        return isRelease;
    }
}
