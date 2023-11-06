package com.zero.studentdashboard.repository;

import com.zero.studentdashboard.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByCountryName(String countryName);
}
