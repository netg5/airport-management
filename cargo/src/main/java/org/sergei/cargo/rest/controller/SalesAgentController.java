package org.sergei.cargo.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.cargo.rest.dto.SalesAgentDTO;
import org.sergei.cargo.rest.dto.response.ResponseWithMetadataDTO;
import org.sergei.cargo.service.interfaces.SalesAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"salesAgentDataTransfer"})
public class SalesAgentController {

    private final SalesAgentService salesAgentService;

    @Autowired
    public SalesAgentController(SalesAgentService salesAgentService) {
        this.salesAgentService = salesAgentService;
    }

    @GetMapping(value = "/getAllSalesAgents")
    public ResponseEntity<ResponseWithMetadataDTO<SalesAgentDTO>> getAllSalesAgents(@RequestParam("page") int page,
                                                                                    @RequestParam("size") int size) {
        return salesAgentService.getAllSalesAgents(page, size);
    }
}
