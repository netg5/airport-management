package org.sergei.manager.service;

import org.sergei.manager.rest.dto.ManufacturerDTO;
import org.sergei.manager.rest.dto.request.ManufacturerRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface ManufacturerService {

    ResponseEntity<ResponseDTO<ManufacturerDTO>> findAll();

    ResponseEntity<ResponseDTO<ManufacturerDTO>> findByCode(ManufacturerRequestDTO request);

    ResponseEntity<ResponseDTO<ManufacturerDTO>> saveManufacturer(ManufacturerDTO request);

    ResponseEntity<ResponseDTO<ManufacturerDTO>> updateManufacturer(ManufacturerDTO request);

    ResponseEntity<ResponseDTO<ManufacturerDTO>> deleteManufacturer(ManufacturerRequestDTO request);
}
