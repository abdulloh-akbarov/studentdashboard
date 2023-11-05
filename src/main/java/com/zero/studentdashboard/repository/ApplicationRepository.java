package com.zero.studentdashboard.repository;

import com.zero.studentdashboard.domain.Application;
import com.zero.studentdashboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByUser(User user);
}
