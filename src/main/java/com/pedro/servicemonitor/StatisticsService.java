package com.pedro.servicemonitor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final CheckResultRepository checkResultRepository;

    public StatisticsService(CheckResultRepository checkResultRepository) {
        this.checkResultRepository = checkResultRepository;
    }

    public void showStatistics(String url) {

        List<CheckResult> results =
                checkResultRepository.findByUrlOrderByCheckedAtAsc(url);

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

        List<Outage> outages = getOutages(url);

        System.out.println("URL: " + url);
        System.out.println("Comprobaciones totales: " + totalChecks);
        System.out.println("Comprobaciones disponibles: " + availableChecks);
        System.out.println("Comprobaciones fallidas: " + failedChecks);
        System.out.printf("Disponibilidad: %.2f%%%n", availabilityPercentage);
        System.out.printf("Tiempo medio de respuesta: %.2f ms%n",
                averageResponseTime);
        System.out.println("Caidas detectadas: " + outages.size());

        for (Outage outage : outages) {
            System.out.println("  - " + outage);
        }
    }

    public List<Outage> getOutages(String url) {

        List<CheckResult> results =
                checkResultRepository.findByUrlOrderByCheckedAtAsc(url);

        return detectOutages(results);
    }

    private List<Outage> detectOutages(List<CheckResult> results) {

        List<Outage> outages = new ArrayList<>();

        LocalDateTime outageStart = null;

        for (CheckResult result : results) {

            if (!result.isAvailable() && outageStart == null) {

                outageStart = result.getCheckedAt();

            } else if (result.isAvailable() && outageStart != null) {

                outages.add(new Outage(
                        outageStart,
                        result.getCheckedAt()));

                outageStart = null;
            }
        }

        if (outageStart != null) {
            outages.add(new Outage(outageStart, null));
        }

        return outages;
    }
}