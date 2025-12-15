package com.codersforcoders.llmrouter.llm.clients;

import com.codersforcoders.llmrouter.llm.LLMClient;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ClaudeClient implements LLMClient {
    private final WebClient webClient;

    @Override
    public String query(String prompt) {
        // call localhost:7050 with body {message: prompt, model: "claude"} and return
        // the response
        String response = webClient.post()
                .uri("http://localhost:7050")
                .bodyValue(new HashMap<String, String>() {
                    {
                        put("message", prompt);
                        put("model", "claude");
                    }
                })
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }

    @Override
    public String getModelName() {
        return "claude";
    }
}
