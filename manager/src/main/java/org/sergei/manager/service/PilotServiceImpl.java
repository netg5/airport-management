package org.sergei.manager.service;

import org.sergei.manager.jpa.model.Pilot;
import org.sergei.manager.jpa.repository.PilotRepository;
import org.sergei.manager.rest.dto.PilotDTO;
import org.sergei.manager.rest.dto.mappers.PilotDTOListMapper;
import org.sergei.manager.rest.dto.mappers.PilotDTOMapper;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO<PilotDTO>> findById(Long pilotId) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO<PilotDTO>> save(PilotDTO pilotDTO) {
        Pilot pilot = pilotModelMapper(pilotDTO);
        Pilot savedPilot = pilotRepository.save(pilot);
        PilotDTO savedPilotDTO = pilotDTOMapper.apply(savedPilot);
        return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(savedPilotDTO)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO<PilotDTO>> update(PilotDTO pilotDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO<PilotDTO>> delete(PilotDTO pilotDTO) {
        return null;
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
