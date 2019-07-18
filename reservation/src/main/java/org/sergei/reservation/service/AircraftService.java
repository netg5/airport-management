package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.dto.AircraftResponseDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface AircraftService {
    ResponseEntity<AircraftResponseDTO> findOne(Long aircraftId);

    ResponseEntity<AircraftResponseDTO> findOneByMultipleParams(HttpServletRequest request);

    ResponseEntity<AircraftResponseDTO> findAll();

    ResponseEntity<AircraftResponseDTO> findAllPaginated(int page, int size);

    ResponseEntity<AircraftResponseDTO> save(AircraftDTO aircraftDTO);

    ResponseEntity<AircraftResponseDTO> update(Long aircraftId, AircraftDTO aircraftDTO);

    ResponseEntity<AircraftResponseDTO> patch(Long aircraftId, Map<String, Object> params);

    ResponseEntity<AircraftResponseDTO> delete(Long aircraftId);
}
