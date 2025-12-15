package com.codersforcoders.llmrouter.routing;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("defaultRule")
public class DefaultRule extends AbstractRoutingRule {
    @Override
    public Optional<String> handle(String prompt) {
        // fallback model
        return Optional.of("gpt");
    }
}
