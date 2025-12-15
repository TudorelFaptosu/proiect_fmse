package com.codersforcoders.llmrouter.llm;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LLMFactoryTest {

    @Test
    void testCreateShouldReturnCorrectClient() {
        LLMClient gptClient = mock(LLMClient.class);
        when(gptClient.getModelName()).thenReturn("gpt");

        LLMFactory factory = new LLMFactory(List.of(gptClient));
        LLMClient result = factory.create("gpt");

        assertNotNull(result);
        assertEquals("gpt", result.getModelName());
    }

    @Test
    void testCreateShouldReturnNullForUnknownModel() {
        LLMClient gptClient = mock(LLMClient.class);
        when(gptClient.getModelName()).thenReturn("gpt");

        LLMFactory factory = new LLMFactory(List.of(gptClient));
        assertNull(factory.create("claude"));
    }
}
