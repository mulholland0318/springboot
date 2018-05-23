package com.springbootredis.springbootredis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisSubscirbeDemo {
    public static void main(String[] args) {
        Jedis jedis = JedisManager.getMgr().getResource();
        MyListener l = new MyListener();
        jedis.subscribe(l, "foo");
//        jedis.publish("foo","fooMessage");


    }





}
class MyListener extends JedisPubSub{

    @Override
    public void onMessage(String channel, String message) {
        System.out.println(channel);
        System.out.println(message);
        super.onMessage(channel, message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(channel);
        System.out.println(message);
        super.onPMessage(pattern, channel, message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("ee");
        super.onSubscribe(channel, subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        super.onUnsubscribe(channel, subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        super.onPUnsubscribe(pattern, subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        super.onPSubscribe(pattern, subscribedChannels);
    }

    @Override
    public void onPong(String pattern) {
        super.onPong(pattern);
    }
}