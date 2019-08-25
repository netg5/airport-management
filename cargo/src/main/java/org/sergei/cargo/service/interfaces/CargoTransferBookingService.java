package org.sergei.cargo.service.interfaces;

import org.sergei.cargo.rest.dto.CargoTransferBookingDTO;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface CargoTransferBookingService {

    ResponseEntity<ResponseDTO<CargoTransferBookingDTO>> makeBooking(CargoTransferBookingDTO request);

}
