package org.sergei.cargo.service;

import org.sergei.cargo.jpa.model.CargoTransferBooking;
import org.sergei.cargo.jpa.model.mappers.CargoTransferBookingModelMapper;
import org.sergei.cargo.jpa.repository.CargoTransferBookingRepository;
import org.sergei.cargo.rest.dto.CargoTransferBookingDTO;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.sergei.cargo.service.interfaces.CargoTransferBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public class CargoTransferBookingServiceImpl implements CargoTransferBookingService {

    private final CargoTransferBookingRepository cargoTransferBookingRepository;
    private final CargoTransferBookingModelMapper cargoTransferBookingModelMapper;

    @Autowired
    public CargoTransferBookingServiceImpl(CargoTransferBookingRepository cargoTransferBookingRepository,
                                           CargoTransferBookingModelMapper cargoTransferBookingModelMapper) {
        this.cargoTransferBookingRepository = cargoTransferBookingRepository;
        this.cargoTransferBookingModelMapper = cargoTransferBookingModelMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<CargoTransferBooking>> makeBooking(CargoTransferBookingDTO request) {
        return null;
    }
}
