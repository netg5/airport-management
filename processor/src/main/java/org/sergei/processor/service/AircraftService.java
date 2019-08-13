package org.sergei.processor.service;

import org.sergei.processor.jpa.model.Aircraft;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface AircraftService {

    /**
     * Get available aircraft and perform its "reservation"
     *
     * @return available pilot
     */
    Aircraft getAvailableAircraft();
}
