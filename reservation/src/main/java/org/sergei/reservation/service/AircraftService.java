package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.AircraftResponseDTO;
import org.sergei.reservation.rest.dto.AircraftUpdateRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface AircraftService {
    ResponseEntity<ResponseDTO<AircraftResponseDTO>> findOne(Long aircraftId);

    ResponseEntity<ResponseDTO<AircraftResponseDTO>> findAll();

    ResponseEntity<ResponseDTO<AircraftResponseDTO>> findAllPaginated(int page, int size);

    ResponseEntity<ResponseDTO<AircraftResponseDTO>> save(AircraftResponseDTO aircraftDTO);

    ResponseEntity<ResponseDTO<AircraftResponseDTO>> update(AircraftUpdateRequestDTO request);

    ResponseEntity<ResponseDTO<AircraftResponseDTO>> patch(Long aircraftId, Map<String, Object> params);

    ResponseEntity<ResponseDTO<AircraftResponseDTO>> delete(Long aircraftId);
}
