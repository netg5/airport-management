package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.AircraftDTO;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface AircraftService {
    AircraftDTO findOne(Long aircraftId);

    AircraftDTO findOneByMultipleParams(HttpServletRequest request);

    List<AircraftDTO> findAll();

    Page<AircraftDTO> findAllPaginated(int page, int size);

    AircraftDTO save(AircraftDTO aircraftDTO);

    AircraftDTO update(Long aircraftId, AircraftDTO aircraftDTO);

    AircraftDTO patch(Long aircraftId, Map<String, Object> params);

    AircraftDTO delete(Long aircraftId);
}
