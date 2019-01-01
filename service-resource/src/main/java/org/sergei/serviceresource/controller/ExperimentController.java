package org.sergei.serviceresource.controller;

import org.sergei.serviceresource.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping(value = "/experiment", produces = "application/json")
public class ExperimentController {

    private final ExperimentService experimentService;

    @Autowired
    public ExperimentController(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @GetMapping
    public List<Long> getAllCustomerIds() {
        return experimentService.getAllCustomerIds();
    }
}
