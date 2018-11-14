/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightservice.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightservice.dto.AircraftDTO;
import org.sergei.flightservice.exceptions.ResourceNotFoundException;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.repository.AircraftRepository;
import org.sergei.flightservice.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class AircraftService {

    private final ModelMapper modelMapper;
    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftService(ModelMapper modelMapper, AircraftRepository aircraftRepository) {
        this.modelMapper = modelMapper;
        this.aircraftRepository = aircraftRepository;
    }

    /**
     * Find aircraft by ID
     *
     * @param aircraftId gets aircraft ID as parameter
     * @return aircraft DTO
     */
    public AircraftDTO findOne(Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aicraft with this ID not found")
                );
        return modelMapper.map(aircraft, AircraftDTO.class);
    }

    /**
     * Find all aircrafts
     *
     * @return list of Aircraft DTO
     */
    public List<AircraftDTO> findAll() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        return ObjectMapperUtils.mapAll(aircraftList, AircraftDTO.class);
    }

    /**
     * Save aircraft
     *
     * @param aircraftDTO get aircraft DTO as a parameter
     * @return Aircraft DTO
     */
    public AircraftDTO save(AircraftDTO aircraftDTO) {
        Aircraft aircraft = modelMapper.map(aircraftDTO, Aircraft.class);
        aircraftRepository.save(aircraft);
        return aircraftDTO;
    }

    /**
     * Update aicraft by ID
     *
     * @param aircraftId  get aircraft ID as a parameter
     * @param aircraftDTO get aircraft DTO as a parameter
     * @return aircraft DTO
     */
    public AircraftDTO update(Long aircraftId, AircraftDTO aircraftDTO) {
        aircraftDTO.setAircraftId(aircraftId);

        Aircraft aircraft = modelMapper.map(aircraftDTO, Aircraft.class);
        aircraftRepository.save(aircraft);

        return aircraftDTO;
    }

    /**
     * Delete aircraft by ID
     *
     * @param aircraftId get aircraft ID as a parameter
     * @return aircraft DTO as a response
     */
    public AircraftDTO delete(Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aicraft with this ID not found")
                );
        aircraftRepository.delete(aircraft);
        return modelMapper.map(aircraft, AircraftDTO.class);
    }
}