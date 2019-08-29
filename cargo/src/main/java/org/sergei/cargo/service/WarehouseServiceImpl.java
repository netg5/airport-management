package org.sergei.cargo.service;

import org.sergei.cargo.jpa.model.Warehouse;
import org.sergei.cargo.jpa.repository.WarehouseRepository;
import org.sergei.cargo.rest.dto.WarehouseDTO;
import org.sergei.cargo.rest.dto.mappers.WarehouseDTOListMapper;
import org.sergei.cargo.rest.dto.response.FacetCountDTO;
import org.sergei.cargo.rest.dto.response.FacetFieldsDTO;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.sergei.cargo.rest.dto.response.ResponseWithMetadataDTO;
import org.sergei.cargo.service.interfaces.WarehouseService;
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
@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseDTOListMapper warehouseDTOListMapper;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository,
                                WarehouseDTOListMapper warehouseDTOListMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseDTOListMapper = warehouseDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseWithMetadataDTO<WarehouseDTO>> getAllWarehouses(int page, int size) {
        Page<Warehouse> allWarehouses = warehouseRepository.findAll(PageRequest.of(page, size));
        List<WarehouseDTO> warehouseDTOList = warehouseDTOListMapper.apply(allWarehouses.getContent());

        ResponseWithMetadataDTO<WarehouseDTO> response =
                ResponseWithMetadataDTO.<WarehouseDTO>builder()
                        .generalResponse(ResponseDTO.<WarehouseDTO>builder()
                                .errorList(List.of())
                                .response(warehouseDTOList)
                                .build())
                        .facetCount(FacetCountDTO.builder()
                                .facetFields(FacetFieldsDTO.builder()
                                        .resNum(allWarehouses.getTotalElements())
                                        .build())
                                .build())
                        .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
