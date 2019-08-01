package org.sergei.manager.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.manager.rest.dto.PilotDTO;
import org.sergei.manager.rest.dto.request.PilotRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping("/pilots")
@Api(tags = {"pilotCrudOperations"})
public class PilotController {

    private final PilotService pilotService;

    @Autowired
    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<ResponseDTO<PilotDTO>> findAll() {
        return pilotService.findAll();
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<PilotDTO>> findPilotById(@RequestBody PilotRequestDTO request) {
        return pilotService.findById(request);
    }

    @PostMapping(value = "/save", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<PilotDTO>> savePilot(@RequestBody PilotDTO request) {
        return pilotService.save(request);
    }

    @PostMapping(value = "/update", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<PilotDTO>> updatePilot(@RequestBody PilotDTO pilotDTO) {
        return pilotService.update(pilotDTO);
    }

    @DeleteMapping("/{pilotId}")
    public ResponseEntity<ResponseDTO<PilotDTO>> deletePilot(@PathVariable Long pilotId) {
        return pilotService.delete(pilotId);
    }
}
