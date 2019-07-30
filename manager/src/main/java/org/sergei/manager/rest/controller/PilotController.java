package org.sergei.manager.rest.controller;

import org.sergei.manager.rest.dto.PilotDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping("/pilots")
public class PilotController {

    private final PilotService pilotService;

    @Autowired
    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<PilotDTO>> savePilot(@RequestBody PilotDTO request) {
        return pilotService.save(request);
    }
}
