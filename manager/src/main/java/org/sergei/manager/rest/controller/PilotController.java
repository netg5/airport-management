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
@Api(tags = {"pilotCrudOperations"})
public class PilotController {

    private final PilotService pilotService;

    @Autowired
    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @GetMapping(value = "/findAllPilots")
    public ResponseEntity<ResponseDTO<PilotDTO>> findAllPilots() {
        return pilotService.findAll();
    }

    @PostMapping(value = "/findPilotById")
    public ResponseEntity<ResponseDTO<PilotDTO>> findPilotById(@RequestBody PilotRequestDTO request) {
        return pilotService.findById(request);
    }

    @PostMapping(value = "/savePilot")
    public ResponseEntity<ResponseDTO<PilotDTO>> savePilot(@RequestBody PilotDTO request) {
        return pilotService.save(request);
    }

    @PostMapping(value = "/updatePilot")
    public ResponseEntity<ResponseDTO<PilotDTO>> updatePilot(@RequestBody PilotDTO pilotDTO) {
        return pilotService.update(pilotDTO);
    }

    @DeleteMapping(value = "/deletePilot/{pilotId}")
    public ResponseEntity<ResponseDTO<PilotDTO>> deletePilot(@PathVariable Long pilotId) {
        return pilotService.delete(pilotId);
    }
}
