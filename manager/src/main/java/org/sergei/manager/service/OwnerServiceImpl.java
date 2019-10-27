package org.sergei.manager.service;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.sergei.manager.jpa.model.Owner;
import org.sergei.manager.jpa.model.mappers.OwnerModelMapper;
import org.sergei.manager.jpa.repository.OwnerRepository;
import org.sergei.manager.rest.dto.OwnerDTO;
import org.sergei.manager.rest.dto.mappers.OwnerDTOListMapper;
import org.sergei.manager.rest.dto.mappers.OwnerDTOMapper;
import org.sergei.manager.rest.dto.request.OwnerRequestDTO;
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
@Slf4j
@Service
public class OwnerServiceImpl implements OwnerService {

    private final MessageService messageService;
    private final OwnerRepository ownerRepository;
    private final OwnerModelMapper ownerModelMapper;
    private final OwnerDTOMapper ownerDTOMapper;
    private final OwnerDTOListMapper ownerDTOListMapper;

    @Autowired
    public OwnerServiceImpl(MessageService messageService,
                            OwnerRepository ownerRepository,
                            OwnerModelMapper ownerModelMapper,
                            OwnerDTOMapper ownerDTOMapper,
                            OwnerDTOListMapper ownerDTOListMapper) {
        this.messageService = messageService;
        this.ownerRepository = ownerRepository;
        this.ownerModelMapper = ownerModelMapper;
        this.ownerDTOMapper = ownerDTOMapper;
        this.ownerDTOListMapper = ownerDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<OwnerDTO>> findAll() {
        List<Owner> ownerList = ownerRepository.findAll();
        if (ownerList.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            List<OwnerDTO> aircraftDTOList = ownerDTOListMapper.apply(ownerList);

            ResponseDTO<OwnerDTO> response = new ResponseDTO<>();
            response.setErrorList(ImmutableList.of());
            response.setResponse(aircraftDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<OwnerDTO>> findById(OwnerRequestDTO request) {
        Long ownerId = request.getOwnerId();
        if (ownerId == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("OW-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        }

        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if (owner.isEmpty()) {
            log.debug("Owner with ID: {} not found", ownerId);
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            OwnerDTO ownerDTO = ownerDTOMapper.apply(owner.get());
            ResponseDTO<OwnerDTO> response = new ResponseDTO<>();
            response.setErrorList(ImmutableList.of());
            response.setResponse(List.of(ownerDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<OwnerDTO>> save(OwnerDTO ownerDTO) {
        Owner owner = ownerModelMapper.apply(ownerDTO);
        Owner savedOwner = ownerRepository.save(owner);
        OwnerDTO savedOwnerDTO = ownerDTOMapper.apply(savedOwner);
        return new ResponseEntity<>(new ResponseDTO<>(ImmutableList.of(), List.of(savedOwnerDTO)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO<OwnerDTO>> update(OwnerDTO ownerDTO) {
        Long ownerId = ownerDTO.getId();
        if (ownerId == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.OK);
        } else {
            Optional<Owner> owner = ownerRepository.findById(ownerId);
            if (owner.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("OW-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.OK);
            } else {
                Owner ownerUpdated = ownerModelMapper.apply(ownerDTO);
                Owner ownerSaved = ownerRepository.save(ownerUpdated);
                OwnerDTO ownerSavedDTO = ownerDTOMapper.apply(ownerSaved);

                ResponseDTO<OwnerDTO> response = new ResponseDTO<>();
                response.setErrorList(ImmutableList.of());
                response.setResponse(List.of(ownerSavedDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<OwnerDTO>> delete(Long ownerId) {
        if (ownerId == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Owner> owner = ownerRepository.findById(ownerId);
            if (owner.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            } else {
                ownerRepository.delete(owner.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }
}
