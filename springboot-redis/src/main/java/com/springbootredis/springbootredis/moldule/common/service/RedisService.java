package com.springbootredis.springbootredis.moldule.common.service;


import com.springbootredis.springbootredis.moldule.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void save(String str){
        redisTemplate.opsForValue().set("test",str);
    }

    private Set<User> users = new HashSet<User>();

    @CachePut(value="user", key="'user:'+#user.id")
    public User addUser(User user){
        users.add(user);
        return user;
    }

    @Cacheable(value="user",key="'User:'+#id")
    public User addUser(String id, String name, int age){
        User user = new User(id,name,age);
        users.add(user);
        return user;
    }

    @Cacheable(value="user", key="'User:'+#id")
    public User getUser(String id){
        System.out.println("not in redis cache");
        User user = null;
        for(User user1: users){
            if(user1.getId().equals(id)){
                user = user1;
            }
        }
        return user;
    }

}
