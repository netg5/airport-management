package org.sergei.manager.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sergei.manager.rest.dto.FlyModeDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.FlyModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@Api(tags = {"FlyModeRetrieval"})
@RestController
public class FlyModeController {

    private final FlyModeService flyModeService;

    @Autowired
    public FlyModeController(FlyModeService flyModeService) {
        this.flyModeService = flyModeService;
    }

    @ApiOperation(value = "Get all existing fly modes")
    @GetMapping(value = "/getAllFlyModes")
    public ResponseEntity<ResponseDTO<FlyModeDTO>> getAllFlyModes() {
        return flyModeService.findAllFlyModes();
    }

    @ApiOperation(value = "Get all existing fly modes")
    @GetMapping(value = "/getFlyModeByCode")
    public ResponseEntity<ResponseDTO<FlyModeDTO>> getFlyModeByCode(@RequestParam("code") String code) {
        return flyModeService.findFlyModeByCode(code);
    }

}
