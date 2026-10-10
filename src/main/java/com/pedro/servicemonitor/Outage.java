package com.pedro.servicemonitor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Outage {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Outage(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isRecovered() {
        return endTime != null;
    }

    public long getDurationSeconds() {

        if (!isRecovered()) {
            return 0;
        }

        return Duration.between(startTime, endTime).getSeconds();
    }

    @Override
    public String toString() {

        String formattedStartTime = startTime.format(DATE_FORMATTER);

        if (isRecovered()) {

            String formattedEndTime = endTime.format(DATE_FORMATTER);

            long totalSeconds = getDurationSeconds();

            long minutes = totalSeconds / 60;
            long seconds = totalSeconds % 60;

            return "Caida iniciada: " + formattedStartTime
                    + " | Recuperacion: " + formattedEndTime
                    + " | Duracion: " + minutes + " min "
                    + seconds + " s";
        }

        return "Caida iniciada: " + formattedStartTime
                + " | Servicio todavia no recuperado";
    }
}