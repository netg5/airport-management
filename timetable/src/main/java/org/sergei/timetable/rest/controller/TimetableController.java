package org.sergei.timetable.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.timetable.rest.dto.TimetableDTO;
import org.sergei.timetable.rest.dto.response.ResponseDTO;
import org.sergei.timetable.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"timetableOperations"})
public class TimetableController {

    private final TimetableService timetableService;

    @Autowired
    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @GetMapping(value = "/getTimetable")
    public ResponseEntity<ResponseDTO<TimetableDTO>> getTimetable(@RequestParam("page") int page,
                                                                  @RequestParam("size") int size) {
        ResponseDTO<TimetableDTO> response = timetableService.showTimetable(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
