package com.codersforcoders.llmrouter.service;

import com.codersforcoders.llmrouter.llm.LLMClient;
import com.codersforcoders.llmrouter.llm.LLMFactory;
import com.codersforcoders.llmrouter.model.Interaction;
import com.codersforcoders.llmrouter.observer.RouterObserver;
import com.codersforcoders.llmrouter.repository.InteractionRepository;
import com.codersforcoders.llmrouter.routing.RoutingRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RouterServiceTest {

    @Mock
    private InteractionRepository interactionRepository;

    @Mock
    private LLMFactory llmFactory;

    @Mock
    private RoutingRule routingRule;

    @Mock
    private LLMClient llmClient;

    @Mock
    private RouterObserver observer;

    @InjectMocks
    private RouterService routerService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        routerService = new RouterService(interactionRepository, llmFactory, routingRule);

        // adaugă manual observer-ul mock
        ReflectionTestUtils.setField(routerService, "observers", List.of(observer));
    }
    @Test
    void testRouteShouldCallLLMClientAndSaveInteraction() {
        String prompt = "Explain polymorphism";
        String model = "gpt";
        String response = "Polymorphism allows different behaviors.";

        when(routingRule.handle(prompt)).thenReturn(Optional.of(model));
        when(llmFactory.create(model)).thenReturn(llmClient);
        when(llmClient.query(prompt)).thenReturn(response);

        String result = routerService.route(prompt);

        assertEquals(response, result);
        verify(interactionRepository, times(1)).save(any(Interaction.class));
        verify(llmClient, times(1)).query(prompt);
        verify(observer, atLeastOnce()).onEvent(eq("routed"), anyMap());
    }

    @Test
    void testRouteFallbackToGPTIfModelFails() {
        String prompt = "Python function";
        String model = "codellama";

        when(routingRule.handle(prompt)).thenReturn(Optional.of(model));
        when(llmFactory.create(model)).thenReturn(llmClient);
        when(llmClient.query(prompt)).thenThrow(new RuntimeException("Model down"));

        LLMClient gptClient = mock(LLMClient.class);
        when(gptClient.query(prompt)).thenReturn("Fallback success");
        when(llmFactory.create("gpt")).thenReturn(gptClient);

        String response = routerService.route(prompt);

        assertEquals("Fallback success", response);
        verify(llmClient, times(1)).query(prompt);
        verify(gptClient, times(1)).query(prompt);
    }

    @Test
    void testGetAllInteractionsShouldReturnList() {
        List<Interaction> mockList = List.of(new Interaction());
        when(interactionRepository.findAll()).thenReturn(mockList);

        List<Interaction> result = routerService.getAllInteractions();

        assertEquals(1, result.size());
        verify(interactionRepository, times(1)).findAll();
    }
}
