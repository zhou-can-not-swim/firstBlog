package com.zqy.demo_java.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class JedisConfiguration {

    @Value("${diy.redis.password}")
    private String password;

    @Bean
    @ConfigurationProperties(value = "diy.redis")
    public JedisPoolConfig jedisPoolConfig(){
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${diy.redis.host}") String host){
        return new JedisPool(jedisPoolConfig(),host,6379,2000,password);
    }


}
