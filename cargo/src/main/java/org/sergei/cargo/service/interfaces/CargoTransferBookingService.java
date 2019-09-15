package org.sergei.cargo.service.interfaces;

import org.sergei.cargo.rest.dto.request.CargoTransferBookingRequestDTO;
import org.sergei.cargo.rest.dto.response.CargoTransferBookingResponseDTO;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface CargoTransferBookingService {

    /**
     * Perform cargo flight booking with all necessary validations
     *
     * @param request payload
     * @return response
     */
    ResponseEntity<ResponseDTO<CargoTransferBookingResponseDTO>> makeBooking(CargoTransferBookingRequestDTO request);

}
