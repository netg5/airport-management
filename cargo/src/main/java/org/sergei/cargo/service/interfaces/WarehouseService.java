package org.sergei.cargo.service.interfaces;

import org.sergei.cargo.rest.dto.WarehouseDTO;
import org.sergei.cargo.rest.dto.response.ResponseWithMetadataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface WarehouseService {

    /**
     * Get all warehouses for UI with specified request page and number of elements per page
     *
     * @param page number of the page
     * @param size number of elements per page
     * @return response entity
     */
    ResponseEntity<ResponseWithMetadataDTO<WarehouseDTO>> getAllWarehouses(int page, int size);
}
