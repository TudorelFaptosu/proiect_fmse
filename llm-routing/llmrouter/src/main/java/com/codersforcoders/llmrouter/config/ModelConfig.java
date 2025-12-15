package com.codersforcoders.llmrouter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "llm")
@Data
public class ModelConfig {
    private Map<String, String> apiKeys;
    private Map<String, String> apiUrls;
}
