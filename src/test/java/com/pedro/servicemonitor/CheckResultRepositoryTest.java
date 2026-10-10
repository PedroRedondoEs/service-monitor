
package com.pedro.servicemonitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {
        "monitor.enabled=false",
        "spring.datasource.url=jdbc:h2:mem:service-monitor-repository-test"
})
@Transactional
class CheckResultRepositoryTest {

    @Autowired
    private CheckResultRepository repository;

    @Test
    void shouldSaveAndReadCheckResult() {

        CheckResult result = new CheckResult(
                "https://ejemplo.com",
                LocalDateTime.now(),
                true,
                200,
                150
        );

        CheckResult savedResult = repository.save(result);

        assertNotNull(savedResult.getId());

        CheckResult recoveredResult = repository.findById(
                savedResult.getId()
        ).orElseThrow();

        assertEquals("https://ejemplo.com", recoveredResult.getUrl());
        assertTrue(recoveredResult.isAvailable());
        assertEquals(200, recoveredResult.getStatusCode());
        assertEquals(150, recoveredResult.getResponseTimeMs());
        assertNotNull(recoveredResult.getCheckedAt());
    }
}
