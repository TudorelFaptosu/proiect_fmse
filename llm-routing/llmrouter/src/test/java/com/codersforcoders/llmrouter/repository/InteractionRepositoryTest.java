package com.codersforcoders.llmrouter.repository;

import com.codersforcoders.llmrouter.model.Interaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.OffsetDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InteractionRepositoryTest {

    @Autowired
    private InteractionRepository repository;

    @Test
    void testSaveAndFindByModelName() {
        Interaction i = Interaction.builder()
                .promptText("Explain inheritance")
                .modelName("gpt")
                .responseText("Inheritance allows classes to share behavior.")
                .createdAt(OffsetDateTime.now())
                .build();

        repository.save(i);
        List<Interaction> found = repository.findByModelName("gpt");

        assertFalse(found.isEmpty());
        assertEquals("gpt", found.get(0).getModelName());
    }
}
