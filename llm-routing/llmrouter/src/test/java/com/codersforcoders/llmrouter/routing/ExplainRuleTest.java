package com.codersforcoders.llmrouter.routing;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class ExplainRuleTest {

    @Test
    void testHandleReturnsClaudeForExplainPrompt() {
        ExplainRule rule = new ExplainRule();
        Optional<String> result = rule.handle("Explain code example");
        assertTrue(result.isPresent());
        assertEquals("claude", result.get());
    }

    @Test
    void testHandleCallsNextIfNoMatch() {
        ExplainRule rule = new ExplainRule();

        RoutingRule next = new RoutingRule() {
            @Override
            public void setNext(RoutingRule next) {
                // nu ai nevoie de implementare aici
            }

            @Override
            public Optional<String> handle(String prompt) {
                return Optional.of("gpt");
            }
        };

        rule.setNext(next);

        Optional<String> result = rule.handle("Just write something");
        assertEquals("gpt", result.orElse(""));
    }
}
