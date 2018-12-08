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
    private static final String AIRCRAFT_NOT_FOUND = "Aircraft with this ID not found";
    private final ModelMapper modelMapper;
    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftService(ModelMapper modelMapper, AircraftRepository aircraftRepository) {
        this.modelMapper = modelMapper;
        this.aircraftRepository = aircraftRepository;
    }

    /**
     * Find aircraftDTO by ID
     *
     * @param aircraftId gets aircraftDTO ID as parameter
     * @return aircraftDTO DTO
     */
    public AircraftDTO findOne(Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
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
     * Save aircraftDTO
     *
     * @param aircraftDTO get aircraftDTO DTO as a parameter
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
     * @param aircraftId  get aircraftDTO ID as a parameter
     * @param aircraftDTO get aircraftDTO DTO as a parameter
     * @return aircraftDTO DTO
     */
    public AircraftDTO update(Long aircraftId, AircraftDTO aircraftDTO) {
        aircraftDTO.setAircraftId(aircraftId);

        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );

        aircraft.setAircraftId(aircraftId);
        aircraft.setAircraftName(aircraftDTO.getAircraftName());
        aircraft.setModel(aircraftDTO.getModel());
        aircraft.setAircraftWeight(aircraftDTO.getAircraftWeight());
        aircraft.setMaxPassengers(aircraftDTO.getMaxPassengers());

        aircraftRepository.save(aircraft);

        return aircraftDTO;
    }

    /**
     * Delete aircraftDTO by ID
     *
     * @param aircraftId get aircraftDTO ID as a parameter
     * @return aircraftDTO DTO as a response
     */
    public AircraftDTO delete(Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );
        aircraftRepository.delete(aircraft);
        return modelMapper.map(aircraft, AircraftDTO.class);
    }
}