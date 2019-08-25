package org.sergei.cargo.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.cargo.rest.dto.WarehouseDTO;
import org.sergei.cargo.rest.dto.response.ResponseWithMetadataDTO;
import org.sergei.cargo.service.interfaces.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"warehouseDataTransfer"})
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping(value = "/getAllWarehouses")
    public ResponseEntity<ResponseWithMetadataDTO<WarehouseDTO>> getAllWarehouses(@RequestParam("page") int page,
                                                                                  @RequestParam("size") int size) {
        return warehouseService.getAllWarehouses(page, size);
    }

}
