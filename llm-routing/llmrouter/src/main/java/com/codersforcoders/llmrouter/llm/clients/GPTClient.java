package com.codersforcoders.llmrouter.llm.clients;

import com.codersforcoders.llmrouter.llm.LLMClient;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GPTClient implements LLMClient {
    private final WebClient webClient;

    @Override
    public String query(String prompt) {
        // call localhost:7050 with body {message: prompt, model: "gpt"} and return
        // the response
        String response = webClient.post()
                .uri("http://localhost:7050")
                .bodyValue(new HashMap<String, String>() {
                    {
                        put("message", prompt);
                        put("model", "gpt");
                    }
                })
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }

    @Override
    public String getModelName() {
        return "gpt";
    }
}
