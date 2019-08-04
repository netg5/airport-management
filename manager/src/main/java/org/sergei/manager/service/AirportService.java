package org.sergei.manager.service;

import org.sergei.manager.rest.dto.AirportContactDTO;
import org.sergei.manager.rest.dto.AirportDTO;
import org.sergei.manager.rest.dto.request.AirportRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sergei Visotsky
 */
public interface AirportService {

    ResponseEntity<ResponseDTO<AirportDTO>> getAirportByName(AirportRequestDTO request);

    /**
     * Method to get airport contact name and contact job by airport name
     *
     * @param request with single field - airportName
     * @return
     */
    ResponseEntity<ResponseDTO<AirportContactDTO>> getAirportContactByAirportName(AirportRequestDTO request);

    ResponseEntity<ResponseDTO<AirportDTO>> saveAirportData(AirportDTO airportDTO);

}
