/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
public class RouteExtendedDTO extends RouteDTO {
    private List<AircraftDTO> aircraftDTOList = new LinkedList<>();

    public RouteExtendedDTO() {
    }

    public RouteExtendedDTO(List<AircraftDTO> aircraftDTOList) {
        this.aircraftDTOList = aircraftDTOList;
    }

    public List<AircraftDTO> getAircraftDTOList() {
        return aircraftDTOList;
    }

    public void setAircraftDTOList(List<AircraftDTO> aircraftDTOList) {
        this.aircraftDTOList = aircraftDTOList;
    }
}
