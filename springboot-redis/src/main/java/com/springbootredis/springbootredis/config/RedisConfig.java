package com.springbootredis.springbootredis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootredis.springbootredis.moldule.common.CacheKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.core.env.PropertySource;

//启用该配置后 springboot的redis默认配置生效，配置文件中的设置不起作用
//@Configuration
//@EnableAutoConfiguration
//@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.sentinel.nodes}")
      private String redisNodes;

    @Value("${spring.redis.sentinel.master}")
    private String master;

    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration(org.springframework.core.env.PropertySource propertySource){
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration(propertySource);
             String[] host = redisNodes.split(",");
              for(String redisHost : host){
                       String[] item = redisHost.split(":");
                    String ip = item[0];
                     String port = item[1];
                      configuration.addSentinel(new RedisNode(ip, Integer.parseInt(port)));
                 }
              configuration.setMaster(master);
        return configuration;
    }


    @Bean
    public CacheManager redisCacheManager(JedisConnectionFactory jedisConnectionFactory){
        RedisCacheManager redisCacheManager =  RedisCacheManager.create(jedisConnectionFactory);
        return redisCacheManager;
    }

    @Bean
    public JedisClientConfiguration jedisClientConfiguration(){
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.defaultConfiguration();

        return jedisClientConfiguration;
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        //StringRedisTemplate的构造方法中默认设置了stringSerializer
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //设置开启事务
        template.setEnableTransactionSupport(true);
        //set key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //set value serializer
//        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.setConnectionFactory(jedisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }



    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisSentinelConfiguration redisSentinelConfiguration) {
        JedisConnectionFactory factory = new JedisConnectionFactory(redisSentinelConfiguration);
        factory.afterPropertiesSet();
        return factory;
    }

    //重写cache key的默认方法
    @Nullable
    @Override
    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object o, Method method, Object... objects) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(method.getName()).append(".");
//                for (Object obj : objects) {
//
//                    sb.append(obj.hashCode());
//                }
//                return sb.toString();
//            }
//        };
        //存在问题  当缓存方法中的参数是对象时 缓存数据重复
        return new CacheKeyGenerator();
    }
}
