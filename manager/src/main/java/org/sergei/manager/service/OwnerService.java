package org.sergei.manager.service;

import org.sergei.manager.rest.dto.OwnerDTO;
import org.sergei.manager.rest.dto.OwnerRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface OwnerService {

    /**
     * Find all owners
     *
     * @return all owner responses
     */
    ResponseEntity<ResponseDTO<OwnerDTO>> findAll();

    /**
     * Find owner by ID
     *
     * @param request with owner ID
     * @return response with owner
     */
    ResponseEntity<ResponseDTO<OwnerDTO>> findById(OwnerRequestDTO request);

    /**
     * Save owner
     *
     * @param ownerDTO owner request
     * @return saved owner response
     */
    ResponseEntity<ResponseDTO<OwnerDTO>> save(OwnerDTO ownerDTO);

    /**
     * Update owner
     *
     * @param ownerDTO owner to update
     * @return updated owner
     */
    ResponseEntity<ResponseDTO<OwnerDTO>> update(OwnerDTO ownerDTO);

    /**
     * Delete owner by ID
     *
     * @param ownerId request with ID
     * @return response
     */
    ResponseEntity<ResponseDTO<OwnerDTO>> delete(Long ownerId);
}
