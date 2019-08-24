package org.sergei.cargo.service.interfaces;

import org.sergei.cargo.rest.dto.WarehouseDTO;
import org.sergei.cargo.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface WarehouseService {
    ResponseEntity<ResponseDTO<WarehouseDTO>> getAllWarehouses();
}
