package com.springbootredis.springbootredis;

import com.springbootredis.springbootredis.moldule.common.model.User;
import com.springbootredis.springbootredis.moldule.common.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class SpringbootRedisApplicationTests {

	private Logger logger= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RedisService redisService;

	@Test
	public void contextLoads() {
		User user = new User("dsfsd", "zhangsan", 12);
		redisService.addUser(user);
		logger.info("RedisTest执行完成，return {}",redisService.getUser(user.getId()).getId());
	}

}
