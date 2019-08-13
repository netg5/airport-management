package org.sergei.processor.service;

import org.sergei.processor.jpa.model.Pilot;
import org.sergei.processor.jpa.repository.PilotRepository;
import org.sergei.processor.rest.exceptions.FlightRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class PilotServiceImpl implements PilotService {

    private final PilotRepository pilotRepository;

    @Autowired
    public PilotServiceImpl(PilotRepository pilotRepository) {
        this.pilotRepository = pilotRepository;
    }

    @Override
    public Pilot getAvailablePilot() {
        List<Pilot> availablePilots = pilotRepository.getAllAvailablePilots();
        if (availablePilots.isEmpty()) {
            throw new FlightRuntimeException("No available pilots found");
        } else {
            Pilot availablePilot = availablePilots.get(0);
            pilotRepository.updatePilotStatusById(availablePilot.getId());
            return availablePilot;
        }
    }
}
