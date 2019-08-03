package org.sergei.manager.service;

import org.sergei.manager.rest.dto.AircraftDTO;
import org.sergei.manager.rest.dto.request.AircraftRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sergei Visotsky
 */
public interface AircraftService {

    ResponseEntity<ResponseDTO<AircraftDTO>> findAll();

    ResponseEntity<ResponseDTO<AircraftDTO>> findByModelNumber(AircraftRequestDTO request);

    ResponseEntity<ResponseDTO<AircraftDTO>> save(AircraftDTO aircraftDTO);
}
