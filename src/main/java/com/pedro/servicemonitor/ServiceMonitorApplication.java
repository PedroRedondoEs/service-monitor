
package com.pedro.servicemonitor;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceMonitorApplication implements CommandLineRunner {

    private final CheckResultRepository checkResultRepository;
    private final StatisticsService statisticsService;
    private final ReportGenerator reportGenerator;
    private final MonitoringService monitoringService;

    @Value("${monitor.enabled:true}")
    private boolean monitoringEnabled;

    public ServiceMonitorApplication(
            CheckResultRepository checkResultRepository,
            StatisticsService statisticsService,
            ReportGenerator reportGenerator,
            MonitoringService monitoringService) {

        this.checkResultRepository = checkResultRepository;
        this.statisticsService = statisticsService;
        this.reportGenerator = reportGenerator;
        this.monitoringService = monitoringService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceMonitorApplication.class, args);
    }

    @Override
    public void run(String... args) {

        if (!monitoringEnabled) {
            System.out.println(
                    "Monitorizacion desactivada durante las pruebas.");
            return;
        }

        System.out.println();
        System.out.println("=== SERVICE MONITOR ===");
        System.out.println();

        ServiceFileReader fileReader = new ServiceFileReader();

        try {
            List<WebService> services = fileReader.readServices();

            System.out.println("Servicios cargados: " + services.size());
            System.out.println();

            monitoringService.monitorServices(services);

            long totalResults = checkResultRepository.count();

            System.out.println(
                    "Comprobaciones guardadas en el historico: "
                    + totalResults);

            System.out.println();
            System.out.println("=== ESTADISTICAS ===");
            System.out.println();

            for (WebService service : services) {
                statisticsService.showStatistics(service.getUrl());
                System.out.println();
            }

            reportGenerator.generateReport();

        } catch (IOException e) {
            System.err.println(
                    "Error al leer los servicios: " + e.getMessage());
        }
    }
}
