package org.sergei.manager.service;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.sergei.manager.jpa.model.Airport;
import org.sergei.manager.jpa.model.mappers.AirportModelMapper;
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
@Slf4j
@Service
public class AirportServiceImpl implements AirportService {

    @PersistenceContext
    private EntityManager em;

    private final AirportRepository airportRepository;
    private final MessageService messageService;
    private final AirportDOTMapper airportDTOMapper;
    private final AirportModelMapper airportModelMapper;

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository,
                              MessageService messageService,
                              AirportDOTMapper airportDTOMapper,
                              AirportModelMapper airportModelMapper) {
        this.airportRepository = airportRepository;
        this.messageService = messageService;
        this.airportDTOMapper = airportDTOMapper;
        this.airportModelMapper = airportModelMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<AirportDTO>> getAirportByName(AirportRequestDTO request) {
        String airportName = request.getAirportName();
        if (airportName == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Airport> airport = airportRepository.getAirportByName(airportName);
            if (airport.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("APT-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            } else {
                AirportDTO airportDTO = airportDTOMapper.apply(airport.get());
                return new ResponseEntity<>(new ResponseDTO<>(ImmutableList.of(), List.of(airportDTO)), HttpStatus.OK);
            }
        }
    }

    /**
     * Method to get airport facility's contact name and contact job by airport name
     *
     * @param request with single field - airportName
     * @return
     */
    @Override
    public ResponseEntity<ResponseDTO<AirportContactDTO>> getAirportContactByAirportName(AirportRequestDTO request) {
        String airportName = request.getAirportName();
        if (airportName == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            Query query =
                    em.createNativeQuery(
                            "SELECT a.contact_name, a.contact_job FROM airport a WHERE a.airport_name = :airportName");
            query.setParameter("airportName", airportName);
            if (query.getSingleResult() == null) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            } else {
                @SuppressWarnings("unchecked")
                List<Object[]> list = query.getResultList();
                String contactName = null;
                String contactJob = null;
                for (Object[] elem : list) {
                    contactName = (String) elem[0];
                    contactJob = (String) elem[1];
                }
                AirportContactDTO contactDTO = new AirportContactDTO(contactName, contactJob);
                return new ResponseEntity<>(new ResponseDTO<>(ImmutableList.of(), List.of(contactDTO)), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<AirportDTO>> saveAirportData(AirportDTO airportDTO) {
        Airport airport = airportModelMapper.apply(airportDTO);
        Airport savedAirport = airportRepository.save(airport);
        AirportDTO savedAirportDTO = airportDTOMapper.apply(savedAirport);
        return new ResponseEntity<>(new ResponseDTO<>(ImmutableList.of(), List.of(savedAirportDTO)), HttpStatus.OK);
    }
}
