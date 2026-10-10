package com.pedro.servicemonitor;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {

    private final CheckResultRepository checkResultRepository;

    @Value("${monitor.rounds:2}")
    private int numberOfRounds;

    @Value("${monitor.interval-seconds:5}")
    private long intervalSeconds;

    public MonitoringService(CheckResultRepository checkResultRepository) {
        this.checkResultRepository = checkResultRepository;
    }

    public void checkServices(List<WebService> services) {

        ServiceChecker serviceChecker = new ServiceChecker();

        for (WebService service : services) {

            CheckResult result = serviceChecker.check(service);

            checkResultRepository.save(result);

            System.out.println("Resultado guardado en el historico.");
            System.out.println();
        }
    }

    public void monitorServices(List<WebService> services) {

        for (int round = 1; round <= numberOfRounds; round++) {

            System.out.println();
            System.out.println("=== RONDA DE MONITORIZACION "
                    + round + " DE " + numberOfRounds + " ===");
            System.out.println();

            checkServices(services);

            if (round < numberOfRounds) {

                System.out.println(
                        "Esperando " + intervalSeconds
                        + " segundos hasta la siguiente ronda...");

                try {
                    Thread.sleep(intervalSeconds * 1000L);
                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                    System.err.println(
                            "La monitorizacion ha sido interrumpida.");

                    return;
                }
            }
        }
    }
}