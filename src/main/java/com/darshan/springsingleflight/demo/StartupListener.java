package com.darshan.springsingleflight.demo;

import com.darshan.springsingleflight.demo.service.InflateDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupListener {
    private final InflateDataService inflateDataService;

    @EventListener(ApplicationReadyEvent.class)
        public void onReady() {
            inflateDataService.inflateData();
        }
}
