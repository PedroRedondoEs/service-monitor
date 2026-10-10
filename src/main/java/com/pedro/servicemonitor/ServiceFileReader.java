package com.pedro.servicemonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ServiceFileReader {

    public List<WebService> readServices() throws IOException {

        List<WebService> services = new ArrayList<>();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("services.txt");

        if (inputStream == null) {
            throw new IOException("No se ha encontrado el fichero services.txt");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (!line.isEmpty() && !line.startsWith("#")) {
                    services.add(new WebService(line));
                }
            }
        }

        return services;
    }
}