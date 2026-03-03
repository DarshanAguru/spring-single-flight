package com.darshan.springsingleflight.config;

import com.darshan.springsingleflight.aspect.SingleFlightAspect;
import com.darshan.springsingleflight.core.InFlightRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Auto-configuration for SingleFlight.
 * Automatically registers the aspect, registry, and thread pool executor
 * so that the library is ready to use just by adding the dependency.
 */
@AutoConfiguration
@EnableConfigurationProperties(SingleFlightProperties.class)
public class SingleFlightAutoConfiguration {

    @Bean(name = "singleFlightExecutor")
    @ConditionalOnMissingBean(name = "singleFlightExecutor")
    public Executor singleFlightExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("sf-");
        executor.initialize();
        return executor;
    }

    @Bean
    @ConditionalOnMissingBean
    public InFlightRegistry inFlightRegistry(SingleFlightProperties properties) {
        return new InFlightRegistry(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public SingleFlightAspect singleFlightAspect(InFlightRegistry registry,
            @Qualifier("singleFlightExecutor") Executor executor) {
        return new SingleFlightAspect(registry, executor);
    }
}
