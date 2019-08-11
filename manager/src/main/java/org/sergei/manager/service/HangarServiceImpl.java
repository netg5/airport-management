package org.sergei.manager.service;

import lombok.extern.slf4j.Slf4j;
import org.sergei.manager.jpa.model.Aircraft;
import org.sergei.manager.jpa.model.Hangar;
import org.sergei.manager.jpa.repository.HangarRepository;
import org.sergei.manager.rest.dto.HangarDTO;
import org.sergei.manager.rest.dto.mappers.AircraftDTOListMapper;
import org.sergei.manager.rest.dto.mappers.HangarDTOListMapper;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
public class HangarServiceImpl implements HangarService {

    private final MessageService messageService;
    private final HangarRepository hangarRepository;
    private final HangarDTOListMapper hangarDTOListMapper;
    private final AircraftDTOListMapper aircraftDTOListMapper;

    @Autowired
    public HangarServiceImpl(MessageService messageService,
                             HangarRepository hangarRepository,
                             HangarDTOListMapper hangarDTOListMapper,
                             AircraftDTOListMapper aircraftDTOListMapper) {
        this.messageService = messageService;
        this.hangarRepository = hangarRepository;
        this.hangarDTOListMapper = hangarDTOListMapper;
        this.aircraftDTOListMapper = aircraftDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<HangarDTO>> findHangarsByCapacity(Integer capacity) {
        if (capacity == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<Hangar> hangar = hangarRepository.findHangarsByCapacity(capacity);
            if (hangar.isEmpty()) {
                log.debug("Hangar with capacity: {} not found", hangar.get(0).getCapacity());
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("HAN-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                List<HangarDTO> hangarDTOList = hangarDTOListMapper.apply(hangar);
                ResponseDTO<HangarDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(hangarDTOList);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<HangarDTO>> findHangarsByCapacityWithAircrafts(Integer capacity, int page, int size) {
        if (capacity == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Page<Hangar> hangarList = hangarRepository.findHangarsByCapacityWithAircrafts(capacity, PageRequest.of(page, size));
            if (hangarList.isEmpty()) {
                log.debug("Hangar with capacity: {} not found", hangarList.getContent().get(0).getCapacity());
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("HAN-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                List<HangarDTO> hangarDTOList = hangarDTOListMapper.apply(hangarList.getContent());
                hangarList.forEach(
                        hangar -> {
                            List<Aircraft> aircraftList = hangar.getAircrafts();
                            hangarDTOList.forEach(hangarDTO -> hangarDTO.setAircraft(aircraftDTOListMapper.apply(aircraftList)));
                        }
                );
                ResponseDTO<HangarDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(hangarDTOList);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }
}
