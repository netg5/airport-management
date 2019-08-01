package org.sergei.manager.service;

import org.sergei.manager.jpa.repository.ManufacturerRepository;
import org.sergei.manager.rest.dto.ManufacturerDTO;
import org.sergei.manager.rest.dto.request.ManufacturerRequestDTO;
import org.sergei.manager.rest.dto.mappers.ManufacturerDTOListMapper;
import org.sergei.manager.rest.dto.mappers.ManufacturerDTOMapper;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerDTOMapper manufacturerDTOMapper;
    private final ManufacturerDTOListMapper manufacturerDTOListMapper;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository,
                                   ManufacturerDTOMapper manufacturerDTOMapper,
                                   ManufacturerDTOListMapper manufacturerDTOListMapper) {
        this.manufacturerRepository = manufacturerRepository;
        this.manufacturerDTOMapper = manufacturerDTOMapper;
        this.manufacturerDTOListMapper = manufacturerDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> findByCode(ManufacturerRequestDTO request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> saveManufacturer(ManufacturerDTO request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> updateManufacturer(ManufacturerDTO request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> deleteManufacturer(ManufacturerRequestDTO request) {
        throw new UnsupportedOperationException();
    }
}
