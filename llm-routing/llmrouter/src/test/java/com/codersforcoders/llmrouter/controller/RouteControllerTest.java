package com.codersforcoders.llmrouter.controller;

import com.codersforcoders.llmrouter.service.RouterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteControllerTest {

    @Mock
    private RouterService routerService;

    @InjectMocks
    private RouteController routeController;

    @Test
    void route_shouldCacheResponse() {
        String prompt = "test prompt";
        String response = "test response";
        Map<String, String> requestBody = Map.of("prompt", prompt);

        when(routerService.route(prompt)).thenReturn(response);

        // First call - should call service
        ResponseEntity<Map<String, String>> result1 = routeController.route(requestBody);
        assertEquals(response, result1.getBody().get("response"));
        verify(routerService, times(1)).route(prompt);

        // Second call - should use cache
        ResponseEntity<Map<String, String>> result2 = routeController.route(requestBody);
        assertEquals(response, result2.getBody().get("response"));
        verify(routerService, times(1)).route(prompt); // Still 1
    }

    @Test
    void route_shouldEvictOldEntries() {
        // Fill cache with 10 entries
        for (int i = 0; i < 10; i++) {
            String prompt = "prompt" + i;
            String response = "response" + i;
            when(routerService.route(prompt)).thenReturn(response);
            routeController.route(Map.of("prompt", prompt));
        }

        // Add 11th entry
        String prompt11 = "prompt10";
        String response11 = "response10";
        when(routerService.route(prompt11)).thenReturn(response11);
        routeController.route(Map.of("prompt", prompt11));

        // Check if first entry is evicted (should call service again)
        String prompt0 = "prompt0";
        String response0 = "response0";
        // We need to reset the mock or just verify the call count increases
        routeController.route(Map.of("prompt", prompt0));

        // prompt0 was called once in the loop, and now called again.
        // If it was cached, total calls would be 1. If evicted, total calls 2.
        verify(routerService, times(2)).route(prompt0);
    }
}
