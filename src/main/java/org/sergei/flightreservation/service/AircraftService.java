/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.generic.GenericJpaDAO;
import org.sergei.flightreservation.dto.AircraftDTO;
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

    @Autowired
    private ModelMapper modelMapper;

    private GenericJpaDAO<Aircraft> genericDAO;

    @Autowired
    public void setGenericDAO(GenericJpaDAO<Aircraft> genericDAO) {
        this.genericDAO = genericDAO;
        genericDAO.setPersistentClass(Aircraft.class);
    }

    public AircraftDTO findOne(Long aircraftId) {
        Aircraft aircraft = genericDAO.findOne(aircraftId);
        return modelMapper.map(aircraft, AircraftDTO.class);
    }

    public List<AircraftDTO> findAll() {
        List<Aircraft> aircraftList = genericDAO.findAll();
        return ObjectMapperUtils.mapAll(aircraftList, AircraftDTO.class);
    }

    public AircraftDTO save(AircraftDTO aircraftDTO) {
        Aircraft aircraft = modelMapper.map(aircraftDTO, Aircraft.class);
        genericDAO.save(aircraft);
        return aircraftDTO;
    }

    public AircraftDTO update(Long aircraftId, AircraftDTO aircraftDTO) {
        aircraftDTO.setAircraftId(aircraftId);

        Aircraft aircraft = modelMapper.map(aircraftDTO, Aircraft.class);
        genericDAO.update(aircraft);

        return aircraftDTO;
    }

    public AircraftDTO delete(Long aircraftId) {
        Aircraft aircraft = genericDAO.findOne(aircraftId);
        genericDAO.delete(aircraft);
        return modelMapper.map(aircraft, AircraftDTO.class);
    }
}
