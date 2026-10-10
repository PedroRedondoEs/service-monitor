package com.pedro.servicemonitor;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "check_results")
public class CheckResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private LocalDateTime checkedAt;
    private boolean available;
    private int statusCode;
    private long responseTimeMs;

    public CheckResult() {
    }

    public CheckResult(String url, LocalDateTime checkedAt, boolean available,
            int statusCode, long responseTimeMs) {
        this.url = url;
        this.checkedAt = checkedAt;
        this.available = available;
        this.statusCode = statusCode;
        this.responseTimeMs = responseTimeMs;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public long getResponseTimeMs() {
        return responseTimeMs;
    }

    @Override
    public String toString() {
        return "CheckResult{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", checkedAt=" + checkedAt +
                ", available=" + available +
                ", statusCode=" + statusCode +
                ", responseTimeMs=" + responseTimeMs +
                '}';
    }
}