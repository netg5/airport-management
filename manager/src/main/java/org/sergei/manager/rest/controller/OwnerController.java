package org.sergei.manager.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.manager.rest.dto.OwnerDTO;
import org.sergei.manager.rest.dto.OwnerRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping("/owners")
@Api(tags = {"ownerCrudOperations"})
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<ResponseDTO<OwnerDTO>> findAll() {
        return ownerService.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDTO<OwnerDTO>> findOwnerById(@RequestBody OwnerRequestDTO request) {
        return ownerService.findById(request);
    }

    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDTO<OwnerDTO>> saveOwner(@RequestBody OwnerDTO request) {
        return ownerService.save(request);
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDTO<OwnerDTO>> updateOwner(@RequestBody OwnerDTO request) {
        return ownerService.update(request);
    }

    @DeleteMapping(value = "/{ownerId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDTO<OwnerDTO>> deleteOwner(@PathVariable Long ownerId) {
        return ownerService.delete(ownerId);
    }
}
