package org.sergei.manager.service;

import org.sergei.manager.jpa.model.Airport;
import org.sergei.manager.jpa.repository.AirportRepository;
import org.sergei.manager.rest.dto.AirportContactDTO;
import org.sergei.manager.rest.dto.AirportDTO;
import org.sergei.manager.rest.dto.mappers.AirportDOTMapper;
import org.sergei.manager.rest.dto.request.AirportRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Service
public class AirportServiceImpl implements AirportService {

    @PersistenceContext
    private EntityManager em;

    private final AirportRepository airportRepository;
    private final MessageService messageService;
    private final AirportDOTMapper airportDOTMapper;

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository,
                              MessageService messageService, AirportDOTMapper airportDOTMapper) {
        this.airportRepository = airportRepository;
        this.messageService = messageService;
        this.airportDOTMapper = airportDOTMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<AirportDTO>> getAirportByName(AirportRequestDTO request) {
        String airportName = request.getAirportName();
        if (airportName == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Airport> airport = airportRepository.getAirportByName(airportName);
            if (airport.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("APT-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                AirportDTO airportDTO = airportDOTMapper.apply(airport.get());
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(airportDTO)), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<AirportContactDTO>> getAirportContactByAirportName(AirportRequestDTO request) {
        String airportName = request.getAirportName();
        if (airportName == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Query query = em.createNativeQuery("SELECT a.contact_name, a.contact_job FROM airport a WHERE a.airport_name = :airportName");
            query.setParameter("airportName", airportName);
            if (query.getSingleResult() == null) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                String contactName = String.valueOf(query.getParameter("contact_name"));
                String contactJob = String.valueOf(query.getParameter("contact_job"));
                AirportContactDTO contactDTO = new AirportContactDTO(contactName, contactJob);
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(contactDTO)), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<AirportDTO>> saveAirportData(AirportDTO airportDTO) {
        return null;
    }
}
