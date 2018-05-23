package com.springcache.springcachedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringcacheDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcacheDemoApplication.class, args);
	}
}
