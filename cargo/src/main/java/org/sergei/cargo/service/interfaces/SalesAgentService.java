package org.sergei.cargo.service.interfaces;

import org.sergei.cargo.rest.dto.SalesAgentDTO;
import org.sergei.cargo.rest.dto.response.ResponseWithMetadataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface SalesAgentService {

    /**
     * Get all sales agents for UI with specified request page and number of elements per page
     *
     * @param page number of the page
     * @param size number of elements per page
     * @return response entity
     */
    ResponseEntity<ResponseWithMetadataDTO<SalesAgentDTO>> getAllSalesAgents(int page, int size);
}
