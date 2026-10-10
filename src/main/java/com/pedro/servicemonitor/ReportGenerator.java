package com.pedro.servicemonitor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReportGenerator {

    private static final String REPORT_FILE = "service-monitor-report.txt";

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final CheckResultRepository checkResultRepository;
    private final StatisticsService statisticsService;

    public ReportGenerator(
            CheckResultRepository checkResultRepository,
            StatisticsService statisticsService) {

        this.checkResultRepository = checkResultRepository;
        this.statisticsService = statisticsService;
    }

    public void generateReport() {

        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter(REPORT_FILE))) {

            writer.write("=== SERVICE MONITOR - INFORME ===");
            writer.newLine();
            writer.newLine();

            writer.write("Fecha de generacion: "
                    + LocalDateTime.now().format(DATE_FORMATTER));
            writer.newLine();
            writer.newLine();

            List<CheckResult> allResults = checkResultRepository.findAll();

            List<String> urls = allResults.stream()
                    .map(CheckResult::getUrl)
                    .distinct()
                    .sorted()
                    .toList();

            for (String url : urls) {

                List<CheckResult> results =
                        checkResultRepository
                                .findByUrlOrderByCheckedAtAsc(url);

                long totalChecks = results.size();

                long availableChecks = results.stream()
                        .filter(CheckResult::isAvailable)
                        .count();

                long failedChecks = totalChecks - availableChecks;

                double availabilityPercentage = 0.0;

                if (totalChecks > 0) {
                    availabilityPercentage =
                            (availableChecks * 100.0) / totalChecks;
                }

                double averageResponseTime = results.stream()
                        .mapToLong(CheckResult::getResponseTimeMs)
                        .average()
                        .orElse(0.0);

                List<Outage> outages = statisticsService.getOutages(url);

                writer.write("----------------------------------------");
                writer.newLine();

                writer.write("URL: " + url);
                writer.newLine();

                writer.write("Comprobaciones totales: " + totalChecks);
                writer.newLine();

                writer.write("Comprobaciones disponibles: "
                        + availableChecks);
                writer.newLine();

                writer.write("Comprobaciones fallidas: " + failedChecks);
                writer.newLine();

                writer.write(String.format(
                        "Disponibilidad: %.2f%%%n",
                        availabilityPercentage));

                writer.write(String.format(
                        "Tiempo medio de respuesta: %.2f ms%n",
                        averageResponseTime));

                writer.write("Caidas detectadas: " + outages.size());
                writer.newLine();

                for (Outage outage : outages) {
                    writer.write("  - " + outage);
                    writer.newLine();
                }

                writer.newLine();
            }

            writer.write("----------------------------------------");
            writer.newLine();

            System.out.println();
            System.out.println("Informe generado: " + REPORT_FILE);

        } catch (IOException e) {
            System.err.println(
                    "Error al generar el informe: " + e.getMessage());
        }
    }
}