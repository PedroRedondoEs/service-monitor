
package com.pedro.servicemonitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ServiceCheckerTest {

    @Test
    void shouldMarkInvalidUrlAsUnavailable() {

        ServiceChecker checker = new ServiceChecker();

        WebService service = new WebService("esto-no-es-una-url");

        CheckResult result = checker.check(service);

        assertNotNull(result);
        assertFalse(result.isAvailable());
        assertEquals(0, result.getStatusCode());
        assertEquals("esto-no-es-una-url", result.getUrl());
    }

    @Test
    void shouldRecordCheckDateAndTime() {

        ServiceChecker checker = new ServiceChecker();

        WebService service = new WebService("esto-no-es-una-url");

        CheckResult result = checker.check(service);

        assertNotNull(result.getCheckedAt());
    }

    @Test
    void shouldRecordNonNegativeResponseTime() {

        ServiceChecker checker = new ServiceChecker();

        WebService service = new WebService("esto-no-es-una-url");

        CheckResult result = checker.check(service);

        assertTrue(result.getResponseTimeMs() >= 0);
    }

    @Test
    void shouldMarkEmptyUrlAsUnavailable() {

        ServiceChecker checker = new ServiceChecker();

        WebService service = new WebService("");

        CheckResult result = checker.check(service);

        assertNotNull(result);
        assertFalse(result.isAvailable());
        assertEquals(0, result.getStatusCode());
        assertEquals("", result.getUrl());
    }
}
