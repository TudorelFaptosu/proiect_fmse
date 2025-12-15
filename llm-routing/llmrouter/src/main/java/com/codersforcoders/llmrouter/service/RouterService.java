package com.codersforcoders.llmrouter.service;

import com.codersforcoders.llmrouter.llm.LLMClient;
import com.codersforcoders.llmrouter.llm.LLMFactory;
import com.codersforcoders.llmrouter.model.Interaction;
import com.codersforcoders.llmrouter.observer.RouterObserver;
import com.codersforcoders.llmrouter.repository.InteractionRepository;
import com.codersforcoders.llmrouter.routing.RoutingRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RouterService {
    private final InteractionRepository interactionRepository;
    private final LLMFactory llmFactory;
    private final RoutingRule routeChainHead;
    private final List<RouterObserver> observers  = new ArrayList<>();

    @Transactional
    public String route(String prompt) {
        String normalized = normalize(prompt);
        Optional<String> modelOpt = routeChainHead.handle(normalized);
        String model = modelOpt.orElse("gpt");
        LLMClient client = llmFactory.create(model);
        String response;
        try {
            response = client.query(prompt);
        } catch (Exception e) {
            // failover: try fallback model if available
            if (!"gpt".equals(model)) {
                LLMClient fallback = llmFactory.create("gpt");
                try {
                    response = fallback.query(prompt);
                    model = "gpt";
                } catch (Exception ex) {
                    response = "Error: model unavailable";
                }
            } else {
                response = "Error: model unavailable";
            }
        }

        Interaction i = Interaction.builder()
                .promptText(prompt)
                .modelName(model)
                .responseText(response)
                .metadataJson("{}")
                .build();
        interactionRepository.save(i);

        Map<String,Object> ev = new HashMap<>();
        ev.put("model", model);
        ev.put("promptSize", prompt == null ? 0 : prompt.length());
        observers.forEach(o -> o.onEvent("routed", ev));

        return response;
    }

    public List<Interaction> getAllInteractions() {
        return interactionRepository.findAll();
    }

    private String normalize(String prompt) {
        return prompt == null ? "" : prompt.trim();
    }
}
