package com.codersforcoders.llmrouter.config;

import com.codersforcoders.llmrouter.routing.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class RoutingChainConfig {
    @Bean
    public RoutingRule routeChainHead(@Qualifier("explainRule") RoutingRule explainRule,
                                      @Qualifier("pythonRule") RoutingRule pythonRule,
                                      @Qualifier("defaultRule") RoutingRule defaultRule) {
        explainRule.setNext(pythonRule);
        pythonRule.setNext(defaultRule);
        return explainRule;
    }
}
