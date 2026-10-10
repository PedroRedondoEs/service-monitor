package com.pedro.servicemonitor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckResultRepository extends JpaRepository<CheckResult, Long> {

    List<CheckResult> findByUrlOrderByCheckedAtAsc(String url);
}