package com.darshan.springsingleflight.core;

import com.darshan.springsingleflight.config.SingleFlightProperties;
import com.github.benmanes.caffeine.cache.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class InFlightRegistry {


    private final Cache<String, InflightEntry> cache;

    public InFlightRegistry(SingleFlightProperties properties){
        long maxSize = properties.getMaxSize() > 0 ? properties.getMaxSize() : 10_000;
        long resulTtlMs = properties.getResulTtlMs() > 0 ? properties.getResulTtlMs() : 200;
        this.cache = Caffeine
                .newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(resulTtlMs, TimeUnit.MILLISECONDS)
                .build();
    }

    public InflightEntry get(String key)
    {
        return this.cache.getIfPresent(key);
    }

    public InflightEntry putIfAbsent(String key, InflightEntry inflightEntry)
    {

        return this.cache.asMap().putIfAbsent(key, inflightEntry);

    }

    public void remove(String key)
    {
        this.cache.invalidate(key);
    }


}
