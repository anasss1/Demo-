package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CacheService {

    @Autowired
    private ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    // Ajouter un objet dans le cache
    public Mono<Boolean> cacheObject(String key, Object value) {
        return reactiveRedisTemplate.opsForValue()
                .set(key, value)
                .map(result -> result);
    }

    // Récupérer un objet depuis le cache
    public Mono<Object> getCachedObject(String key) {
        return reactiveRedisTemplate.opsForValue()
                .get(key);
    }

    // Supprimer un objet du cache
    public Mono<Boolean> deleteObjectFromCache(String key) {
        return reactiveRedisTemplate.opsForValue()
                .delete(key);
    }
}

