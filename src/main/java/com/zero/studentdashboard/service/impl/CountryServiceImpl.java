package com.zero.studentdashboard.service.impl;

import com.zero.studentdashboard.domain.Country;
import com.zero.studentdashboard.dto.CountryDto;
import com.zero.studentdashboard.model.Response;
import com.zero.studentdashboard.model.enums.Message;
import com.zero.studentdashboard.repository.CountryRepository;
import com.zero.studentdashboard.service.CountryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;

    /**
     * Retrieves all countries.
     *
     * @return A Response object containing a list of all countries.
     */
    @Override
    public Response getAll() {
        log.info(">> getAll: ");

        List<Country> all = repository.findAll();
        if (all.isEmpty()){
            log.warn("<< getAll: No country found");
            return new Response(Message.COUNTRY_NOT_FOUND);
        }

        log.info("<< getAll: All Countries successfully retrieved");
        return new Response(Message.SUCCESS, all);
    }

    /**
     * Saves a new country.
     *
     * @param countryDto The DTO containing country details.
     * @return A Response object indicating the outcome of the save operation.
     * @see CountryDto
     */
    @Override
    @Transactional
    public Response save(CountryDto countryDto) {
        log.info(">> save: " + countryDto);
        boolean existsByName = repository.existsByCountryName(countryDto.countryName());
        if (existsByName) {
            log.warn("<< save: Country exists by name=" + countryDto.countryName());
            return new Response(Message.COUNTRY_EXIST);
        }

        try {
            repository.save(countryDto.toEntity());
            log.info("<< save: Country saved successfully");
            return new Response(Message.SUCCESS);
        } catch (Exception e) {
            log.error("<< save: Exception thrown while saving message: " + e.getMessage());
            return new Response(Message.UNPROCESSABLE);
        }
    }
}
