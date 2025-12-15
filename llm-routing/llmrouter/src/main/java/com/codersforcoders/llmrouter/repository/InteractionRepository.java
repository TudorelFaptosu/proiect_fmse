package com.codersforcoders.llmrouter.repository;

import com.codersforcoders.llmrouter.model.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    List<Interaction> findByModelName(String modelName);
}
