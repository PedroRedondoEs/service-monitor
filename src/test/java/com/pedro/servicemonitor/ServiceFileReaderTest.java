package com.pedro.servicemonitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

class ServiceFileReaderTest {

    @Test
    void shouldReadServicesFromFile() throws IOException {

        ServiceFileReader fileReader = new ServiceFileReader();

        List<WebService> services = fileReader.readServices();

        assertNotNull(services);
        assertEquals(3, services.size());

        assertEquals(
                "https://www.google.com",
                services.get(0).getUrl());

        assertEquals(
                "https://github.com",
                services.get(1).getUrl());

        assertEquals(
                "https://httpbin.org/status/200",
                services.get(2).getUrl());
    }
}