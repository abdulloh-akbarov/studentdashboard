package com.zero.studentdashboard.repository;

import com.zero.studentdashboard.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
