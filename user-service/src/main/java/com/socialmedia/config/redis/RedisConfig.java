//package com.socialmedia.config.redis;
//
//import com.oda.m005cachedenemesi.entity.User;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericToStringSerializer;
//
//@Configuration
//@EnableCaching // Springin Cache mekanizmalarını aktifleştirmek için kullanılır.
////@EnableRedisRepositories // Redisi veritabanı gibi kullanmak istiyorsan eğer bu anotasyon ile kullanabilirsin
//public class RedisConfig {
//
//    // https://docs.spring.io/spring-data/redis/docs/2.7.18/reference/html/#redis:connectors:lettuce  10.4.2
//    @Bean
//    public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, User> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//        template.setValueSerializer(new GenericToStringSerializer<>(User.class)); // serileştirme işlemi
//        return template;
//    }
//
//}
