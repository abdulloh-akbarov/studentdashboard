package com.zero.studentdashboard.repository;

import com.zero.studentdashboard.domain.Course;
import com.zero.studentdashboard.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByUniversity_Id(Long universityId);

    Optional<Course> findByCourseNameAndUniversity(String courseName, University university);
}
