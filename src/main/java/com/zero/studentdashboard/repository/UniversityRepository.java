package com.zero.studentdashboard.repository;

import com.zero.studentdashboard.domain.Country;
import com.zero.studentdashboard.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findAllByCountry_Id(Long countryId);

    Optional<University> findByNameAndCountry(String name, Country country);
}
