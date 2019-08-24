package org.sergei.cargo.service;

import org.sergei.cargo.jpa.model.Warehouse;
import org.sergei.cargo.jpa.repository.WarehouseRepository;
import org.sergei.cargo.rest.dto.WarehouseDTO;
import org.sergei.cargo.rest.dto.mappers.WarehouseDTOListMapper;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.sergei.cargo.service.interfaces.ResponseMessageService;
import org.sergei.cargo.service.interfaces.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private final ResponseMessageService responseMessageService;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseDTOListMapper warehouseDTOListMapper;

    @Autowired
    public WarehouseServiceImpl(ResponseMessageService responseMessageService,
                                WarehouseRepository warehouseRepository,
                                WarehouseDTOListMapper warehouseDTOListMapper) {
        this.responseMessageService = responseMessageService;
        this.warehouseRepository = warehouseRepository;
        this.warehouseDTOListMapper = warehouseDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<WarehouseDTO>> getAllWarehouses() {
        List<Warehouse> allWarehouses = warehouseRepository.findAll();
        List<WarehouseDTO> warehouseDTOList = warehouseDTOListMapper.apply(allWarehouses);

        ResponseDTO<WarehouseDTO> response = new ResponseDTO<>();
        response.setErrorList(List.of());
        response.setResponse(warehouseDTOList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
