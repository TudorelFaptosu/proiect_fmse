package com.codersforcoders.llmrouter.routing;

import java.util.Optional;

public abstract class AbstractRoutingRule implements RoutingRule {
    protected RoutingRule next;

    @Override
    public void setNext(RoutingRule next) {
        this.next = next;
    }

    protected Optional<String> callNext(String prompt) {
        if (next == null) return Optional.empty();
        return next.handle(prompt);
    }
}
