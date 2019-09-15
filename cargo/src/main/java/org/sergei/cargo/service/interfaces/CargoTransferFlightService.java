package org.sergei.cargo.service.interfaces;

import org.sergei.cargo.rest.dto.CargoTransferFlightDTO;
import org.sergei.cargo.rest.dto.response.ResponseWithMetadataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface CargoTransferFlightService {

    /**
     * Get Cargo transfer data for UI paginated with metadata
     *
     * @param page number of page
     * @param size number of elements per page
     * @return response entity
     */
    ResponseEntity<ResponseWithMetadataDTO<CargoTransferFlightDTO>> getAllCargoTransferFlights(int page, int size);

}
