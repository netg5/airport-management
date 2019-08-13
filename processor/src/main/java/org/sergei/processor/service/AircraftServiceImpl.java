package org.sergei.processor.service;

import org.sergei.processor.jpa.model.Aircraft;
import org.sergei.processor.jpa.repository.AircraftRepository;
import org.sergei.processor.rest.exceptions.FlightRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftServiceImpl(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    public Aircraft getAvailableAircraft() {
        List<Aircraft> availableAircrafts = aircraftRepository.getAvailableAllAircrafts();
        if (availableAircrafts.isEmpty()) {
            throw new FlightRuntimeException("No available pilots found");
        } else {
            Aircraft availableAircraft = availableAircrafts.get(0);
            aircraftRepository.updateAircraftStatusById(availableAircraft.getId());
            return availableAircraft;
        }
    }
}
