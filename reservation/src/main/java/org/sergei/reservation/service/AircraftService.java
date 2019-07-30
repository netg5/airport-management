package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.dto.AircraftRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sergei Visotsky
 */
public interface AircraftService {
    ResponseEntity<ResponseDTO<AircraftDTO>> findAll();

    ResponseEntity<ResponseDTO<AircraftDTO>> findOne(AircraftRequestDTO request);
}
