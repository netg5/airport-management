package org.sergei.manager.service;

import org.sergei.manager.rest.dto.HangarDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface HangarService {

    ResponseEntity<ResponseDTO<HangarDTO>> findHangarsByCapacity(Integer capacity);

    ResponseEntity<ResponseDTO<HangarDTO>> findHangarsByCapacityWithAircrafts(Integer capacity);

}
