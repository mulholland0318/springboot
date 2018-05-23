package com.springbootredis.springbootredis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class JedisDemo {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

        Jedis jedis = null;
        jedis = pool.getResource();
        jedis.auth("root");
        jedis.set("foo", "bar");
        String foobar = jedis.get("foo");

        jedis.zadd("sose",0 , "car1");
        jedis.zadd("sose", 0, "bike1");
        Set<String> sose = jedis.zrange("sose", 0, -1);
        try{

        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        String s = "";
        if (jedis == null) {

        }

    }
}
