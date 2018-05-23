package com.springbootredis.springbootredis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.Set;

public class JedisTransactionDemo {
    public static void main(String[] args) {
        Jedis jedis = null;
        try{
            JedisPool pool = new JedisPool("localhost",6379);
            jedis = pool.getResource();
            Transaction t = jedis.multi();
            t.set("foo","bar");
            t.set("foo1","bar22");

            Response<String> result = t.get("foo");
            t.zadd("fo",1,"barowitch");
            t.zadd("fo", 0, "bard");
            t.zadd("fo",3,"dsfs");
            Response<Set<String>> sose = t.zrange("fo",0,-1);



            t.exec();
            String foobar =  result.get();
            int soseSize = sose.get().size();
            System.out.println("footbar:"+foobar+"/sosesize:"+soseSize);

        }finally{
            if(null != jedis){
                jedis.close();
            }
        }
    }
}
