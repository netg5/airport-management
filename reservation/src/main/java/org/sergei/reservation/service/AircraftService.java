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
    ResponseEntity<AircraftDTO> findOne(Long aircraftId) throws ResourceNotFoundException;

    ResponseEntity<AircraftDTO> findOneByMultipleParams(HttpServletRequest request) throws ResourceNotFoundException;

    ResponseEntity<List<AircraftDTO>> findAll() throws ResourceNotFoundException;

    ResponseEntity<List<AircraftDTO>> findAllPaginated(int page, int size) throws ResourceNotFoundException;

    ResponseEntity<AircraftDTO> save(AircraftDTO aircraftDTO) throws ResourceNotFoundException;

    ResponseEntity<AircraftDTO> update(Long aircraftId, AircraftDTO aircraftDTO) throws ResourceNotFoundException;

    ResponseEntity<AircraftDTO> patch(Long aircraftId, Map<String, Object> params) throws ResourceNotFoundException;

    ResponseEntity<AircraftDTO> delete(Long aircraftId) throws ResourceNotFoundException;
}
