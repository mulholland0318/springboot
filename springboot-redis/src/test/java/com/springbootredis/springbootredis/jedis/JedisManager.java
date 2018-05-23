package com.springbootredis.springbootredis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisManager {
    private volatile static JedisManager manager;

    private final JedisPool pool;

    private JedisManager() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        pool = new JedisPool(config,"localhost",6379);
    }

    public static JedisManager getMgr(){
        if(null == manager){
            synchronized (JedisManager.class){
                if(null == manager){
                    manager = new JedisManager();
                }
            }
        }
        return manager;
    }

    public Jedis getResource(){
        return pool.getResource();
    }

    public void destory(){
        pool.destroy();
    }

    public void close(){
        pool.close();
    }
}
