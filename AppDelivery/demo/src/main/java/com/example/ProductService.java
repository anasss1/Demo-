package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ProductRepository productRepository;

    public Mono<Product> getProductById(String productId) {
        String cacheKey = "product:" + productId;

        // Vérifier si le produit existe dans le cache
        return cacheService.getCachedObject(cacheKey)
                .switchIfEmpty(
                    // Si le cache est vide, récupérer les données depuis la base de données et les mettre en cache
                    productRepository.findById(productId)
                            .doOnSuccess(product -> cacheService.cacheObject(cacheKey, product))
                );
    }
}
public Mono<Boolean> cacheObjectWithExpiration(String key, Object value, long seconds) {
    return reactiveRedisTemplate.opsForValue()
            .set(key, value, Duration.ofSeconds(seconds));
}

import java.time.Duration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

public Mono<Boolean> cacheObjectWithTTL(String key, Object value, Duration ttl) {
    return reactiveRedisTemplate.opsForValue()
            .set(key, value, ttl);