/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.AircraftDAO;
import org.sergei.flightreservation.dto.AircraftDTO;
import org.sergei.flightreservation.exceptions.ResourceNotFoundException;
import org.sergei.flightreservation.model.Aircraft;
import org.sergei.flightreservation.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class AircraftService {

    private final ModelMapper modelMapper;
    private final AircraftDAO aircraftDAO;

    @Autowired
    public AircraftService(ModelMapper modelMapper, AircraftDAO aircraftDAO) {
        this.modelMapper = modelMapper;
        this.aircraftDAO = aircraftDAO;
    }

    /**
     * Find aircraft by ID
     *
     * @param aircraftId gets aircraft ID as parameter
     * @return aircraft DTO
     */
    public AircraftDTO findOne(Long aircraftId) {
        Aircraft aircraft = aircraftDAO.findOne(aircraftId);
        if (aircraft == null) {
            throw new ResourceNotFoundException("Aircraft with this ID not found");
        }
        return modelMapper.map(aircraft, AircraftDTO.class);
    }

    /**
     * Find all aircrafts
     *
     * @return list of Aircraft DTO
     */
    public List<AircraftDTO> findAll() {
        List<Aircraft> aircraftList = aircraftDAO.findAll();
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
        aircraftDAO.save(aircraft);
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
        aircraftDAO.update(aircraft);

        return aircraftDTO;
    }

    /**
     * Delete aircraft by ID
     *
     * @param aircraftId get aircraft ID as a parameter
     * @return aircraft DTO as a response
     */
    public AircraftDTO delete(Long aircraftId) {
        Aircraft aircraft = aircraftDAO.findOne(aircraftId);
        if (aircraft == null) {
            throw new ResourceNotFoundException("Aicraft with this ID not found");
        }
        aircraftDAO.delete(aircraft);
        return modelMapper.map(aircraft, AircraftDTO.class);
    }
}
