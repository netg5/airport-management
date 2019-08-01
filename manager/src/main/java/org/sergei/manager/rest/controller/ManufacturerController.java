package org.sergei.manager.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.manager.rest.dto.ManufacturerDTO;
import org.sergei.manager.rest.dto.request.ManufacturerRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping("/manufacturers")
@Api(tags = {"manufacturerCrudOperations"})
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> getAllManufacturers() {
        return manufacturerService.findAll();
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> getManufacturerByCode(@RequestBody ManufacturerRequestDTO request) {
        return manufacturerService.findByCode(request);
    }

    @PostMapping(value = "/save", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> saveManufacturer(@RequestBody ManufacturerDTO request) {
        return manufacturerService.saveManufacturer(request);
    }

    @PostMapping(value = "/update", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> updateManufacturer(@RequestBody ManufacturerDTO request) {
        return manufacturerService.updateManufacturer(request);
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<ResponseDTO<ManufacturerDTO>> deleteManufacturer(@PathVariable String code) {
        return manufacturerService.deleteManufacturer(code);
    }
}
