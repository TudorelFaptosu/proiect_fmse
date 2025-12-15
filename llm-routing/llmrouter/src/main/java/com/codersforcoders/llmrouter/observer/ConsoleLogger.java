package com.codersforcoders.llmrouter.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ConsoleLogger implements RouterObserver {
    @Override
    public void onEvent(String event, Map<String, Object> data) {
        log.info("Event: {} data: {}", event, data);
    }
}
