package com.darshan.springsingleflight.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the SingleFlight cache registry.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "singleflight")
public class SingleFlightProperties {

    /**
     * Time-to-live for the result of a single flight execution in milliseconds.
     * This defines how long the shared result will be kept in the cache to serve
     * concurrent requests that arrive right after the execution finishes.
     */
    private long resulTtlMs = 200;

    /**
     * Maximum number of keys to retain in the registry cache.
     */
    private long maxSize = 10_000;
}
