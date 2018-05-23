package com.springcache.springcachedemo.module;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {


    @RequestMapping("/addOne")
    @CachePut(value="myuser", key="'user'+#user.id")
    public Object addUser(User user){
        log.info("add the user to cache always!");
        return user;
    }

    @RequestMapping("/addTwo")
    @Cacheable(value="myUser", key="'user'+#user.id")
    public Object addTwo(User user){
        log.info("add the user to cache");
        return user;
    }

    @RequestMapping("/findOne")
    @Cacheable(value="myUser", key="'user'+#id")
    public Object findOne(String id){
        log.info("find one user from cache");
        User user = new User();
        user.setId(id);
        return user;
    }
}
