package com.pedro.servicemonitor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

public class ServiceChecker {

    private final HttpClient httpClient;

    public ServiceChecker() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public CheckResult check(WebService service) {

        long startTime = System.nanoTime();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(service.getUrl()))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<Void> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.discarding());

            long responseTime = calculateResponseTime(startTime);

            int statusCode = response.statusCode();
            boolean available = statusCode >= 200 && statusCode < 400;

            System.out.println("URL: " + service.getUrl());
            System.out.println("Estado HTTP: " + statusCode);
            System.out.println("Disponible: "
                    + (available ? "SI" : "NO"));
            System.out.println(
                    "Tiempo de respuesta: " + responseTime + " ms");
            System.out.println();

            return new CheckResult(
                    service.getUrl(),
                    LocalDateTime.now(),
                    available,
                    statusCode,
                    responseTime);

        } catch (IllegalArgumentException e) {

            long responseTime = calculateResponseTime(startTime);

            System.out.println("URL: " + service.getUrl());
            System.out.println("Disponible: NO");
            System.out.println("Error: URL no valida.");
            System.out.println();

            return new CheckResult(
                    service.getUrl(),
                    LocalDateTime.now(),
                    false,
                    0,
                    responseTime);

        } catch (IOException e) {

            long responseTime = calculateResponseTime(startTime);

            String errorMessage = e.getMessage();

            if (errorMessage == null || errorMessage.isBlank()) {
                errorMessage = e.getClass().getSimpleName();
            }

            System.out.println("URL: " + service.getUrl());
            System.out.println("Disponible: NO");
            System.out.println(
                    "Error de conexion: " + errorMessage);
            System.out.println();

            return new CheckResult(
                    service.getUrl(),
                    LocalDateTime.now(),
                    false,
                    0,
                    responseTime);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            long responseTime = calculateResponseTime(startTime);

            System.out.println("URL: " + service.getUrl());
            System.out.println("Disponible: NO");
            System.out.println("Comprobacion interrumpida.");
            System.out.println();

            return new CheckResult(
                    service.getUrl(),
                    LocalDateTime.now(),
                    false,
                    0,
                    responseTime);
        }
    }

    private long calculateResponseTime(long startTime) {

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000;
    }
}