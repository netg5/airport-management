package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface AircraftService {
    AircraftDTO findOne(Long aircraftId) throws ResourceNotFoundException;

    AircraftDTO findOneByMultipleParams(HttpServletRequest request) throws ResourceNotFoundException;

    List<AircraftDTO> findAll() throws ResourceNotFoundException;

    Page<AircraftDTO> findAllPaginated(int page, int size) throws ResourceNotFoundException;

    AircraftDTO save(AircraftDTO aircraftDTO) throws ResourceNotFoundException;

    AircraftDTO update(Long aircraftId, AircraftDTO aircraftDTO) throws ResourceNotFoundException;

    AircraftDTO patch(Long aircraftId, Map<String, Object> params) throws ResourceNotFoundException;

    AircraftDTO delete(Long aircraftId) throws ResourceNotFoundException;
}
