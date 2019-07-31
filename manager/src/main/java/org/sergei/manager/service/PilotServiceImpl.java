package org.sergei.manager.service;

import org.sergei.manager.jpa.model.Pilot;
import org.sergei.manager.jpa.repository.PilotRepository;
import org.sergei.manager.rest.dto.PilotDTO;
import org.sergei.manager.rest.dto.PilotRequestDTO;
import org.sergei.manager.rest.dto.mappers.PilotDTOListMapper;
import org.sergei.manager.rest.dto.mappers.PilotDTOMapper;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Service
public class PilotServiceImpl implements PilotService {

    private final PilotRepository pilotRepository;
    private final MessageService messageService;
    private final PilotDTOMapper pilotDTOMapper;
    private final PilotDTOListMapper pilotDTOListMapper;

    @Autowired
    public PilotServiceImpl(PilotRepository pilotRepository,
                            MessageService messageService,
                            PilotDTOMapper pilotDTOMapper,
                            PilotDTOListMapper pilotDTOListMapper) {
        this.pilotRepository = pilotRepository;
        this.messageService = messageService;
        this.pilotDTOMapper = pilotDTOMapper;
        this.pilotDTOListMapper = pilotDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<PilotDTO>> findAll() {
        List<Pilot> pilot = pilotRepository.findAll();
        if (pilot.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("PIL-002");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<PilotDTO> pilotDTOList = pilotDTOListMapper.apply(pilot);
            ResponseDTO<PilotDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(pilotDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<PilotDTO>> findById(PilotRequestDTO request) {
        Long pilotId = request.getPilotId();
        if (pilotId == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-002");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Pilot> pilot = pilotRepository.findById(pilotId);
            if (pilot.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("PIL-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                PilotDTO pilotDTO = pilotDTOMapper.apply(pilot.get());
                ResponseDTO<PilotDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(pilotDTO));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<PilotDTO>> save(PilotDTO pilotDTO) {
        Double pilotWeight = pilotDTO.getWeight();
        if (pilotWeight > 72) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("PIL-003");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.OK);
        } else {
            Pilot pilot = pilotModelMapper(pilotDTO);
            Pilot savedPilot = pilotRepository.save(pilot);
            PilotDTO savedPilotDTO = pilotDTOMapper.apply(savedPilot);
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(savedPilotDTO)), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<PilotDTO>> update(PilotDTO pilotDTO) {
        Long pilotId = pilotDTO.getId();
        Double pilotWeight = pilotDTO.getWeight();
        if (pilotId == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-002");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            if (pilotWeight > 72) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("PIL-003");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.OK);
            } else {
                Optional<Pilot> pilot = pilotRepository.findById(pilotId);
                if (pilot.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("PIL-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.OK);
                } else {
                    Pilot pilotUpdated = pilotModelMapper(pilotDTO);
                    Pilot savedPilot = pilotRepository.save(pilotUpdated);
                    PilotDTO savedPilotDTO = pilotDTOMapper.apply(savedPilot);
                    return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(savedPilotDTO)), HttpStatus.OK);
                }
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> delete(PilotRequestDTO request) {
        Long pilotId = request.getPilotId();
        if (pilotId == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-002");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Pilot> pilot = pilotRepository.findById(pilotId);
            if (pilot.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("PIL-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.OK);
            } else {
                pilotRepository.delete(pilot.get());
                return new ResponseEntity<>(new ResponseDTO(), HttpStatus.OK);
            }
        }
    }

    private Pilot pilotModelMapper(PilotDTO pilotDTO) {
        return Pilot.builder()
                .licenseNumber(pilotDTO.getLicenseNumber())
                .ssn(pilotDTO.getSsn())
                .firstName(pilotDTO.getFirstName())
                .lastName(pilotDTO.getLastName())
                .gender(pilotDTO.getGender())
                .weight(pilotDTO.getWeight())
                .dateOfBirth(pilotDTO.getDateOfBirth())
                .address(pilotDTO.getAddress())
                .country(pilotDTO.getCountry())
                .email(pilotDTO.getEmail())
                .phone(pilotDTO.getPhone())
                .build();
    }
}
