package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @PostMapping("/put")
    public Mono<Boolean> cacheObject(@RequestParam String key, @RequestParam Object value) {
        return cacheService.cacheObject(key, value);
    }

    @GetMapping("/get")
    public Mono<Object> getCachedObject(@RequestParam String key) {
        return cacheService.getCachedObject(key);
    }

    @DeleteMapping("/delete")
    public Mono<Boolean> deleteObjectFromCache(@RequestParam String key) {
        return cacheService.deleteObjectFromCache(key);
    }
}
