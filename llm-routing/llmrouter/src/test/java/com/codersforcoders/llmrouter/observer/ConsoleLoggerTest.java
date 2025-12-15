package com.codersforcoders.llmrouter.observer;

import org.junit.jupiter.api.Test;
import java.util.Map;

class ConsoleLoggerTest {

    @Test
    void testOnEventDoesNotThrow() {
        ConsoleLogger logger = new ConsoleLogger();
        logger.onEvent("test-event", Map.of("model", "gpt"));
        // dacă nu aruncă excepții, testul trece
    }
}
