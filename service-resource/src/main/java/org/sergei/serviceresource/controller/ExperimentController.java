package org.sergei.serviceresource.controller;

import org.sergei.serviceresource.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Deprecated
@RestController
public class ExperimentController {

    private final ExperimentService experimentService;

    @Autowired
    public ExperimentController(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @GetMapping(value = "/experiment", produces = "application/json")
    public ResponseEntity<List<Long>> getAllCustomerIds() {
        return new ResponseEntity<>(experimentService.getAllCustomerIds(), HttpStatus.OK);
    }


}