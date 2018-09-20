package com.qimiaochong.common.config.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author: NightWish
 * @create: 2018-09-07 10:41
 * @description: Redis配置
 **/
@Configuration
public class RedisConfig {

    /**
     * 自定义 redisTemplate
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //自定义序列化
//        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
//        CustomRedisSerializer customRedisSerializer=new CustomRedisSerializer(Object.class);

//        redisTemplate.setValueSerializer(customRedisSerializer);
//        redisTemplate.setHashValueSerializer(customRedisSerializer);

        //使用genericJackson2JsonRedisSerializer序列化
//        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer());
//        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer());
        //使用fastjson序列化<错误：无法完全序列化>
//        FastJsonRedisSerializer fastJsonRedisSerializer=new FastJsonRedisSerializer(Object.class);
//        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

//        redisTemplate.setValueSerializer(genericFastJsonRedisSerializer());
//        redisTemplate.setHashValueSerializer(genericFastJsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * json序列化
     */
    @Bean
    public RedisSerializer<Object> jackson2JsonRedisSerializer(){
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer serializer=new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper=new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        return serializer;
    }

    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public GenericFastJsonRedisSerializer genericFastJsonRedisSerializer(){
        return new GenericFastJsonRedisSerializer();
    }
}
