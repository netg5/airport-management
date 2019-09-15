package org.sergei.cargo.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.cargo.rest.dto.CargoTransferFlightDTO;
import org.sergei.cargo.rest.dto.response.ResponseWithMetadataDTO;
import org.sergei.cargo.service.interfaces.CargoTransferFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"cargoTransferCrudOperations"})
public class CargoTransferFlightController {

    private final CargoTransferFlightService cargoTransferFlightService;

    @Autowired
    public CargoTransferFlightController(CargoTransferFlightService cargoTransferFlightService) {
        this.cargoTransferFlightService = cargoTransferFlightService;
    }

    @GetMapping(value = "getAllCargoTransferFlights")
    public ResponseEntity<ResponseWithMetadataDTO<CargoTransferFlightDTO>> getAllCargoTransferFlights(@RequestParam("page") int page,
                                                                                                      @RequestParam("size") int size) {
        return cargoTransferFlightService.getAllCargoTransferFlights(page, size);
    }

}
