package org.sergei.manager.service;

import org.sergei.manager.jpa.repository.OwnerRepository;
import org.sergei.manager.rest.dto.OwnerDTO;
import org.sergei.manager.rest.dto.OwnerRequestDTO;
import org.sergei.manager.rest.dto.mappers.OwnerDTOListMapper;
import org.sergei.manager.rest.dto.mappers.OwnerDTOMapper;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerDTOMapper ownerDTOMapper;
    private final OwnerDTOListMapper ownerDTOListMapper;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository,
                            OwnerDTOMapper ownerDTOMapper,
                            OwnerDTOListMapper ownerDTOListMapper) {
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
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO<OwnerDTO>> update(OwnerDTO ownerDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO> delete(OwnerRequestDTO request) {
        return null;
    }
}
