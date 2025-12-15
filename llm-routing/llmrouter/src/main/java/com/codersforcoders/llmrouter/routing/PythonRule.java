package com.codersforcoders.llmrouter.routing;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("pythonRule")
public class PythonRule extends AbstractRoutingRule {
    @Override
    public Optional<String> handle(String prompt) {
        String p = prompt == null ? "" : prompt.toLowerCase();
        if (p.contains("python") || p.contains("pandas") || p.contains("numpy") || p.contains(".py")) {
            return Optional.of("codellama");
        }
        return callNext(prompt);
    }
}
