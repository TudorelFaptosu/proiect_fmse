package com.codersforcoders.llmrouter.routing;

import java.util.Optional;

public interface RoutingRule {
    void setNext(RoutingRule next);
    Optional<String> handle(String prompt);
}
