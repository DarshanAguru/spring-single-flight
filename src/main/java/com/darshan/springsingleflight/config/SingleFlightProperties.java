package com.darshan.springsingleflight.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "singleflight")
public class SingleFlightProperties {
    private long resulTtlMs = 200;
    private long maxSize = 10_000;
}
