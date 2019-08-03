package org.sergei.manager.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.manager.rest.dto.HangarDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.HangarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"getHangarByCapacity"})
public class HangarController {

    private final HangarService hangarService;

    @Autowired
    public HangarController(HangarService hangarService) {
        this.hangarService = hangarService;
    }

    @GetMapping(value = "/findHangarsByCapacity/{capacity}")
    public ResponseEntity<ResponseDTO<HangarDTO>> findHangarsByCapacity(@PathVariable("capacity") Integer capacity) {
        return hangarService.findHangarsByCapacity(capacity);
    }

    @GetMapping(value = "/findHangarsByCapacityWithAircrafts/{capacity}")
    public ResponseEntity<ResponseDTO<HangarDTO>> findHangarsByCapacityWithAircrafts(@PathVariable("capacity") Integer capacity) {
        return hangarService.findHangarsByCapacityWithAircrafts(capacity);
    }
}
