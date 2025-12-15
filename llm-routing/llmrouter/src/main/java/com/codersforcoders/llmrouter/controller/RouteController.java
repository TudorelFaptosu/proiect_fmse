package com.codersforcoders.llmrouter.controller;

import com.codersforcoders.llmrouter.model.Interaction;
import com.codersforcoders.llmrouter.service.RouterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RouteController {
    private final RouterService routerService;
    private final Map<String, String> cache = new java.util.LinkedHashMap<String, String>(16, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > 10;
        }
    };

    @PostMapping("/route")
    public ResponseEntity<Map<String, String>> route(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");
        if (cache.containsKey(prompt)) {
            return ResponseEntity.ok(Map.of("response", cache.get(prompt)));
        }
        String response = routerService.route(prompt);
        cache.put(prompt, response);
        return ResponseEntity.ok(Map.of("response", response));
    }

    @GetMapping("/interactions")
    public ResponseEntity<List<Interaction>> getInteractions() {
        return ResponseEntity.ok(routerService.getAllInteractions());
    }
}
