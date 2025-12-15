package com.codersforcoders.llmrouter.llm;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class LLMFactory {
    private final Map<String, LLMClient> clients;

    public LLMFactory(List<LLMClient> clientBeans) {
        this.clients = clientBeans.stream().collect(Collectors.toMap(LLMClient::getModelName, c -> c));
    }

    public LLMClient create(String modelName) {
        return clients.get(modelName);
    }
}
