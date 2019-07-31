package org.sergei.manager.service;

import org.sergei.manager.rest.dto.AircraftDTO;
import org.sergei.manager.rest.dto.AircraftRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sergei Visotsky
 */
public interface AircraftService {

    ResponseEntity<ResponseDTO<AircraftDTO>> findAll();

    ResponseEntity<ResponseDTO<AircraftDTO>> findById(AircraftRequestDTO request);

    ResponseEntity<ResponseDTO<AircraftDTO>> save(AircraftDTO aircraftDTO);

    ResponseEntity<ResponseDTO<AircraftDTO>> update(AircraftDTO request);

    ResponseEntity<ResponseDTO<AircraftDTO>> delete(Long aircraftId);
}
