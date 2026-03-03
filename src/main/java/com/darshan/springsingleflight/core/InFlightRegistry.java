package com.darshan.springsingleflight.core;

import com.darshan.springsingleflight.config.SingleFlightProperties;
import com.github.benmanes.caffeine.cache.*;

import java.util.concurrent.TimeUnit;

/**
 * The central registry for tracking in-progress SingleFlight method executions.
 * Uses a Caffeine cache to automatically handle size limits and expiration
 * of execution results, enabling concurrent requests to safely share the same
 * future.
 */
public class InFlightRegistry {

    private final Cache<String, InflightEntry> cache;

    public InFlightRegistry(SingleFlightProperties properties) {
        long maxSize = properties.getMaxSize() > 0 ? properties.getMaxSize() : 10_000;
        long resulTtlMs = properties.getResulTtlMs() > 0 ? properties.getResulTtlMs() : 200;
        this.cache = Caffeine
                .newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(resulTtlMs, TimeUnit.MILLISECONDS)
                .build();
    }

    public InflightEntry get(String key) {
        return this.cache.getIfPresent(key);
    }

    public InflightEntry putIfAbsent(String key, InflightEntry inflightEntry) {

        return this.cache.asMap().putIfAbsent(key, inflightEntry);

    }

    public void remove(String key) {
        this.cache.invalidate(key);
    }

}
