package org.sergei.manager.service;

import org.sergei.manager.rest.dto.FlyModeDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface FlyModeService {

    ResponseEntity<ResponseDTO<FlyModeDTO>> findAllFlyModes();

    ResponseEntity<ResponseDTO<FlyModeDTO>> findFlyModeByCode(String code);

}
