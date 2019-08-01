package org.sergei.manager.service;

import org.sergei.manager.rest.dto.PilotDTO;
import org.sergei.manager.rest.dto.request.PilotRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface PilotService {

    /**
     * Find all existing pilots
     *
     * @return response with pilot model
     */
    ResponseEntity<ResponseDTO<PilotDTO>> findAll();

    /**
     * Find one pilot by ID
     *
     * @param request pilot ID holder
     * @return response with pilot
     */
    ResponseEntity<ResponseDTO<PilotDTO>> findById(PilotRequestDTO request);

    /**
     * Save a new pilot
     *
     * @param pilotDTO pilot data to be saved
     * @return response with pilot
     */
    ResponseEntity<ResponseDTO<PilotDTO>> save(PilotDTO pilotDTO);

    /**
     * Update pilot data
     *
     * @param pilotDTO updated pilot data
     * @return updated pilot
     */
    ResponseEntity<ResponseDTO<PilotDTO>> update(PilotDTO pilotDTO);

    /**
     * Delete pilot
     *
     * @param pilotId of the pilot to delete
     * @return response
     */
    ResponseEntity<ResponseDTO<PilotDTO>> delete(Long pilotId);

}
