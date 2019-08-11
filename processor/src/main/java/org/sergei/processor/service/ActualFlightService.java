package org.sergei.processor.service;

import org.sergei.processor.rest.dto.ActualFlightDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public interface ActualFlightService extends Runnable {

    List<ActualFlightDTO> processFlights();

}
