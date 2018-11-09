/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sergei Visotsky, 2018
 */
public class FlightReservationExtendedDTO extends FlightReservationDTO {

    @JsonIgnore
    @Override
    public Long getRouteId() {
        return super.getRouteId();
    }

    @JsonProperty("allReservedRoutes")
    private RouteExtendedDTO routeExtendedDTO;

    public FlightReservationExtendedDTO() {
    }

    public FlightReservationExtendedDTO(RouteExtendedDTO routeExtendedDTO) {
        this.routeExtendedDTO = routeExtendedDTO;
    }

    public RouteExtendedDTO getRouteExtendedDTO() {
        return routeExtendedDTO;
    }

    public void setRouteExtendedDTO(RouteExtendedDTO routeExtendedDTO) {
        this.routeExtendedDTO = routeExtendedDTO;
    }
}
