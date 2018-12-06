package org.sergei.flightservice.service;

import org.sergei.flightservice.exceptions.ResourceNotFoundException;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class AircraftService {

    private static final String AIRCRAFT_NOT_FOUND = "Aircraft with this ID not found";
    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    /**
     * Find aircraft by ID
     *
     * @param aircraftId gets aircraft ID as parameter
     * @return aircraft DTO
     */
    public Aircraft findOne(Long aircraftId) {
        return aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );
    }

    /**
     * Find all aircrafts
     *
     * @return list of Aircraft DTO
     */
    public List<Aircraft> findAll() {
        return aircraftRepository.findAll();
    }

    /**
     * Save aircraft
     *
     * @param aircraft get aircraft DTO as a parameter
     * @return Aircraft DTO
     */
    public Aircraft save(Aircraft aircraft) {
        aircraftRepository.save(aircraft);
        return aircraft;
    }

    /**
     * Update aicraft by ID
     *
     * @param aircraftId get aircraft ID as a parameter
     * @param aircraft   get aircraft DTO as a parameter
     * @return aircraft DTO
     */
    public Aircraft update(Long aircraftId, Aircraft aircraft) {
        aircraft.setAircraftId(aircraftId);

        Aircraft foundAircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );

        foundAircraft.setAircraftId(aircraftId);
        foundAircraft.setAircraftName(aircraft.getAircraftName());
        foundAircraft.setModel(aircraft.getModel());
        foundAircraft.setAircraftWeight(aircraft.getAircraftWeight());
        foundAircraft.setMaxPassengers(aircraft.getMaxPassengers());

        aircraftRepository.save(foundAircraft);

        return aircraft;
    }

    /**
     * Delete aircraft by ID
     *
     * @param aircraftId get aircraft ID as a parameter
     * @return aircraft DTO as a response
     */
    public Aircraft delete(Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AIRCRAFT_NOT_FOUND)
                );
        aircraftRepository.delete(aircraft);
        return aircraft;
    }
}