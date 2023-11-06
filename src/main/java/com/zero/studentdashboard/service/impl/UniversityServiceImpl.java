package com.zero.studentdashboard.service.impl;

import com.zero.studentdashboard.domain.Country;
import com.zero.studentdashboard.domain.University;
import com.zero.studentdashboard.dto.UniversityDto;
import com.zero.studentdashboard.model.Response;
import com.zero.studentdashboard.model.enums.Message;
import com.zero.studentdashboard.repository.CountryRepository;
import com.zero.studentdashboard.repository.UniversityRepository;
import com.zero.studentdashboard.service.UniversityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for university-related operations, such as getting universities by country and saving universities.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository repository;
    private final CountryRepository countryRepository;

    /**
     * Retrieves all universities by the given country ID.
     *
     * @param countryId The ID of the country for which universities are to be retrieved.
     * @return A Response object containing the list of universities or an error message.
     */
    @Override
    public Response getAllByCountry(Long countryId) {
        log.info(">> getAllByCountry: countryId=" + countryId);

        List<University> allByCountryName = repository.findAllByCountry_Id(countryId);
        if (allByCountryName.isEmpty()){
            log.warn("<< getAllByCountry: No university found for countryId=" + countryId);
            return new Response(Message.UNIVERSITY_NOT_FOUND);
        }

        log.info("<< getAllByCountry: get all university found for countryId=" + countryId);
        return new Response(Message.SUCCESS, allByCountryName);
    }

    /**
     * Saves a new university based on the provided university details.
     *
     * @param universityDto The university's details including name and country ID.
     * @return A Response object indicating the outcome of the save process.
     * @see UniversityDto
     */
    @Override
    public Response save(UniversityDto universityDto) {
        log.info(">> save: " + universityDto);

        Country country = countryRepository.findById(universityDto.countryId()).orElse(null);
        if (country == null){
            log.warn("<< getAll: No country found");
            return new Response(Message.COUNTRY_NOT_FOUND);
        }

        University university = repository.findByUniversityNameAndCountry(universityDto.universityName(), country).orElse(null);
        if (university != null){
            log.warn("<< save: No university found");
            return new Response(Message.UNIVERSITY_EXIST);
        }

        try {
            country.getUniversities().add(universityDto.toEntity());
            countryRepository.save(country);
            log.info(">> save: university saved successfully");
            return new Response(Message.SUCCESS);
        } catch (Exception e) {
            log.error("<< save: Exception thrown while saving message: " + e.getMessage());
            return new Response(Message.UNPROCESSABLE);
        }
    }
}
