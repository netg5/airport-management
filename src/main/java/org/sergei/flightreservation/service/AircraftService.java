package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.AircraftDAO;
import org.sergei.flightreservation.dto.AircraftDTO;
import org.sergei.flightreservation.model.Aircraft;
import org.sergei.flightreservation.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftService implements IService<AircraftDTO> {

    private final ModelMapper modelMapper;
    private final AircraftDAO aircraftDAO;

    @Autowired
    public AircraftService(ModelMapper modelMapper, AircraftDAO aircraftDAO) {
        this.modelMapper = modelMapper;
        this.aircraftDAO = aircraftDAO;
    }

    @Override
    public AircraftDTO findOne(Long aircraftId) {
        Aircraft aircraft = aircraftDAO.findOne(aircraftId);
        return modelMapper.map(aircraft, AircraftDTO.class);
    }

    @Override
    public List<AircraftDTO> findAll() {
        List<Aircraft> aircraftList = aircraftDAO.findAll();
        return ObjectMapperUtils.mapAll(aircraftList, AircraftDTO.class);
    }

    @Override
    public AircraftDTO save(AircraftDTO aircraftDTO) {
        Aircraft aircraft = modelMapper.map(aircraftDTO, Aircraft.class);
        aircraftDAO.save(aircraft);
        return aircraftDTO;
    }

    @Override
    public AircraftDTO update(Long aircraftId, AircraftDTO aircraftDTO) {
        aircraftDTO.setAircraftId(aircraftId);

        Aircraft aircraft = modelMapper.map(aircraftDTO, Aircraft.class);
        aircraftDAO.update(aircraft);

        return aircraftDTO;
    }

    @Override
    public AircraftDTO delete(Long aircraftId) {
        Aircraft aircraft = aircraftDAO.findOne(aircraftId);
        aircraftDAO.delete(aircraft);
        return modelMapper.map(aircraft, AircraftDTO.class);
    }
}
