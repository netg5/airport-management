package org.sergei.processor.service;

import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface ActualFlightService extends Runnable {

    void processFlights();

}
