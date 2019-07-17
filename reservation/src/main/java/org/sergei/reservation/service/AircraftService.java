package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface AircraftService {
    ResponseEntity<AircraftDTO> findOne(Long aircraftId);

    ResponseEntity<AircraftDTO> findOneByMultipleParams(HttpServletRequest request);

    ResponseEntity<List<AircraftDTO>> findAll();

    ResponseEntity<List<AircraftDTO>> findAllPaginated(int page, int size);

    ResponseEntity<AircraftDTO> save(AircraftDTO aircraftDTO);

    ResponseEntity<AircraftDTO> update(Long aircraftId, AircraftDTO aircraftDTO);

    ResponseEntity<AircraftDTO> patch(Long aircraftId, Map<String, Object> params);

    ResponseEntity<AircraftDTO> delete(Long aircraftId);
}
