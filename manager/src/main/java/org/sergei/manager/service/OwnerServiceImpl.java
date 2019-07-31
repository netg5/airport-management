package org.sergei.manager.service;

import org.sergei.manager.jpa.model.Owner;
import org.sergei.manager.jpa.repository.OwnerRepository;
import org.sergei.manager.rest.dto.OwnerDTO;
import org.sergei.manager.rest.dto.OwnerRequestDTO;
import org.sergei.manager.rest.dto.mappers.OwnerDTOListMapper;
import org.sergei.manager.rest.dto.mappers.OwnerDTOMapper;
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
public class OwnerServiceImpl implements OwnerService {

    private final MessageService messageService;
    private final OwnerRepository ownerRepository;
    private final OwnerDTOMapper ownerDTOMapper;
    private final OwnerDTOListMapper ownerDTOListMapper;

    @Autowired
    public OwnerServiceImpl(MessageService messageService,
                            OwnerRepository ownerRepository,
                            OwnerDTOMapper ownerDTOMapper,
                            OwnerDTOListMapper ownerDTOListMapper) {
        this.messageService = messageService;
        this.ownerRepository = ownerRepository;
        this.ownerDTOMapper = ownerDTOMapper;
        this.ownerDTOListMapper = ownerDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<OwnerDTO>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO<OwnerDTO>> findById(OwnerRequestDTO request) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO<OwnerDTO>> save(OwnerDTO ownerDTO) {
        Owner owner = ownerModelMapper(ownerDTO);
        Owner savedOwner = ownerRepository.save(owner);
        OwnerDTO savedOwnerDTO = ownerDTOMapper.apply(savedOwner);
        return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(savedOwnerDTO)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO<OwnerDTO>> update(OwnerDTO ownerDTO) {
        Long ownerId = ownerDTO.getId();
        if (ownerId == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.OK);
        } else {
            Optional<Owner> owner = ownerRepository.findById(ownerId);
            if (owner.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("OW-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.OK);
            } else {
                Owner ownerUpdated = ownerModelMapper(ownerDTO);
                Owner ownerSaved = ownerRepository.save(ownerUpdated);
                OwnerDTO ownerSavedDTO = ownerDTOMapper.apply(ownerSaved);

                ResponseDTO<OwnerDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(ownerSavedDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> delete(OwnerRequestDTO request) {
        return null;
    }

    private Owner ownerModelMapper(OwnerDTO ownerDTO) {
        return Owner.builder()
                .firstName(ownerDTO.getFirstName())
                .lastName(ownerDTO.getLastName())
                .gender(ownerDTO.getGender())
                .address(ownerDTO.getAddress())
                .country(ownerDTO.getCountry())
                .email(ownerDTO.getEmail())
                .phone(ownerDTO.getPhone())
                .build();
    }
}