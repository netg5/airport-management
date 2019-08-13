package org.sergei.processor.service;

import org.sergei.processor.jpa.model.Pilot;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface PilotService {

    /**
     * Get available pilot and perform its "reservation"
     *
     * @return available pilot
     */
    Pilot getAvailablePilot();
}
