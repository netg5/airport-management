package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.dto.AircraftUpdateRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface AircraftService {
    ResponseEntity<ResponseDTO<AircraftDTO>> findOne(Long aircraftId);

    ResponseEntity<ResponseDTO<AircraftDTO>> findOneByMultipleParams(HttpServletRequest request);

    ResponseEntity<ResponseDTO<AircraftDTO>> findAll();

    ResponseEntity<ResponseDTO<AircraftDTO>> findAllPaginated(int page, int size);

    ResponseEntity<ResponseDTO<AircraftDTO>> save(AircraftDTO aircraftDTO);

    ResponseEntity<ResponseDTO<AircraftDTO>> update(AircraftUpdateRequestDTO request);

    ResponseEntity<ResponseDTO<AircraftDTO>> patch(Long aircraftId, Map<String, Object> params);

    ResponseEntity<ResponseDTO<AircraftDTO>> delete(Long aircraftId);
}
