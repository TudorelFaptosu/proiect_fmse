package com.codersforcoders.llmrouter.llm;

public interface LLMClient {
    String query(String prompt);

    String getModelName();
}
