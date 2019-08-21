package org.sergei.manager.service;

import org.sergei.manager.rest.dto.FlightDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sergei Visotsky
 */
public interface FlightService {
    ResponseEntity<ResponseDTO<FlightDTO>> findOneFlight(Long flightId);

    ResponseEntity<ResponseDTO<FlightDTO>> findAllFlights();

    ResponseEntity<ResponseDTO<FlightDTO>> save(FlightDTO request);

    ResponseEntity<ResponseDTO<FlightDTO>> update(FlightDTO request);

    ResponseEntity<ResponseDTO<FlightDTO>> delete(Long flightId);
}
