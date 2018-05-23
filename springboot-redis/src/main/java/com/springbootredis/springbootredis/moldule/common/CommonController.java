package com.springbootredis.springbootredis.moldule.common;


import com.springbootredis.springbootredis.config.annotation.MyCacheable;
import com.springbootredis.springbootredis.moldule.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springbootredis.springbootredis.moldule.common.model.User;

@RestController
@Slf4j
@CacheConfig(cacheNames={"test","test3"})
public class CommonController {

    @Autowired
    private RedisService redisService;

   @Autowired
   private RedisProperties properties;

   @RequestMapping("/test")
   public String test(String str){
       redisService.save(str);
       return str;
   }

   @RequestMapping("/add")
   @CachePut(value="user", key = "'user'+#user.id")
   public User add(User user){
       System.out.println("add the user to cache");
        return user;
   }

   @RequestMapping("/addOne")
   @Cacheable(value="user", key="'user'+#user.name")
   public User addOne(User user){
       System.out.println(user.hashCode());
       System.out.println("the cacheable runing");
       return user;
   }

   @RequestMapping("/addTwo")
   @Cacheable(value = "age")
   public Object addTwo(int age){
       return age;
   }

   @RequestMapping("/delete")
   @CacheEvict(value="user",key = "'user'+#id")
   public String delete(String id){
       log.info("delete");
       return "delete";
   }

   @RequestMapping("/deleteAll")
   @CacheEvict(value = "user",allEntries = true,beforeInvocation = true)
   public String deleteAll(){
       log.info("deleteAll");
       return "deleteAll";
   }


   @RequestMapping("/findOne")
   @Cacheable(value = "user",key = "'user'+#id")
   public Object findOne(String id){
       User user = new User();
       user.setId(id);
       log.info("findOne cacheable");
       return user;
   }


   @RequestMapping("/cache")
   @Caching(cacheable = @Cacheable(value="caching",key="'caching'+#user.id"),
           evict = @CacheEvict(value="evict",key="'evict'+#user.id"))
   public Object testCaching(User user){
       log.info("caching =-=========");
       return "caching";
   }


   @RequestMapping("/mycache")
   @MyCacheable
   public User myCache(User user){
       log.info("myCache-------");
       return user;
   }


   @RequestMapping("/properties")
    public Object properties(){
       return this.properties;
   }

}
