package org.sergei.reports.service;

import lombok.extern.slf4j.Slf4j;
import org.sergei.reports.jpa.model.Flight;
import org.sergei.reports.jpa.repository.FlightReportRepository;
import org.sergei.reports.rest.dto.FlightDTO;
import org.sergei.reports.rest.dto.FlightReportDTO;
import org.sergei.reports.rest.dto.mappers.FlightDTOMapper;
import org.sergei.reports.rest.dto.response.ResponseDTO;
import org.sergei.reports.rest.dto.response.ResponseErrorDTO;
import org.sergei.reports.service.interfaces.FlightReportService;
import org.sergei.reports.service.interfaces.ResponseMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
public class FlightReportServiceImpl implements FlightReportService {

    private final FlightReportRepository flightReportRepository;
    private final ResponseMessageService responseMessageService;
    private final FlightDTOMapper flightDTOMapper;

    @Autowired
    public FlightReportServiceImpl(FlightReportRepository flightReportRepository,
                                   ResponseMessageService responseMessageService, FlightDTOMapper flightDTOMapper) {
        this.flightReportRepository = flightReportRepository;
        this.responseMessageService = responseMessageService;
        this.flightDTOMapper = flightDTOMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<FlightReportDTO>> makeFlightReportByFlightId(Long flightId) {
        Optional<Flight> flight = flightReportRepository.makeFlightReportByFlightId(flightId);
        if (flight.isEmpty()) {
            List<ResponseErrorDTO> errorList = responseMessageService.responseErrorListByCode("AIR-001");
            return new ResponseEntity<>(new ResponseDTO<>(errorList, List.of()), HttpStatus.OK);
        } else {
            FlightDTO flightDTO = flightDTOMapper.apply(flight.get());
            FlightReportDTO flightReportDTO = new FlightReportDTO(flightDTO);
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(flightReportDTO)), HttpStatus.OK);
        }
    }
}
