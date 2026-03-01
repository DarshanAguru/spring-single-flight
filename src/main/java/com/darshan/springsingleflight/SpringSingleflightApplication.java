package com.darshan.springsingleflight;

import com.darshan.springsingleflight.config.SingleFlightProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SingleFlightProperties.class)
public class SpringSingleflightApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSingleflightApplication.class, args);
    }

}
