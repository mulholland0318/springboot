package com.springbootredis.springbootredis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class JedisTest {


    public static void main(String[] args) {
        Set sentinels = new HashSet();
        sentinels.add(new HostAndPort("192.168.20.40",26379).toString());
        sentinels.add(new HostAndPort("192.168.20.40",36379).toString());
        sentinels.add(new HostAndPort("192.168.20.40",46379).toString());


        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster",sentinels);
        Jedis master = sentinelPool.getResource();
        String uuid = UUID.randomUUID().toString();
        master.set("username", uuid);
        try {
            System.out.println("sleep 30s  begin");
            Thread.sleep(30000);
            System.out.println("sleep 30s  end!!!");
        } catch (Exception Exc) {

            Exc.printStackTrace();
            System.exit(0);
        }


        Jedis master2 = sentinelPool.getResource();
        String value = master2.get("username");
        System.out.println("get->username: " + value);


    }

}
