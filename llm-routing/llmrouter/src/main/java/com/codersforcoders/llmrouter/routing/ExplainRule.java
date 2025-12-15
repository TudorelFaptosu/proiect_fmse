package com.codersforcoders.llmrouter.routing;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("explainRule")
public class ExplainRule extends AbstractRoutingRule {
    @Override
    public Optional<String> handle(String prompt) {
        String p = prompt == null ? "" : prompt.toLowerCase();
        if (p.contains("explain") || p.contains("explain code") || p.contains("what is")) {
            return Optional.of("claude");
        }
        return callNext(prompt);
    }
}
