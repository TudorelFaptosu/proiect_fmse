package com.codersforcoders.llmrouter.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "interaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prompt_text", columnDefinition = "text", nullable = false)
    private String promptText;

    @Column(name = "model_name", length = 100, nullable = false)
    private String modelName;

    @Column(name = "response_text", columnDefinition = "text")
    private String responseText;

    // Store metadata as JSON string for simplicity
    @Column(name = "metadata", columnDefinition = "text")
    private String metadataJson;

    @Column(name = "created_at", columnDefinition = "timestamp with time zone")
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }
}
