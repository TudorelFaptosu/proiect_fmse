package com.codersforcoders.llmrouter.observer;

import java.util.Map;

public interface RouterObserver {
    void onEvent(String event, Map<String, Object> data);
}
