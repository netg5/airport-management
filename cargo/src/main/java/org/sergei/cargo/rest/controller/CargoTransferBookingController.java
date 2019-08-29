package org.sergei.cargo.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.cargo.rest.dto.CargoTransferBookingDTO;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.sergei.cargo.service.interfaces.CargoTransferBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"cargoTransferBooking"})
public class CargoTransferBookingController {

    private final CargoTransferBookingService cargoTransferBookingService;

    @Autowired
    public CargoTransferBookingController(CargoTransferBookingService cargoTransferBookingService) {
        this.cargoTransferBookingService = cargoTransferBookingService;
    }

    @PostMapping(value = "/bookCargoTransferFlight")
    public ResponseEntity<ResponseDTO<CargoTransferBookingDTO>> bookCargoTransferFlight(@RequestBody CargoTransferBookingDTO request) {
        return cargoTransferBookingService.makeBooking(request);
    }

}
