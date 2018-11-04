/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dto;

/**
 * @author Sergei Visotsky, 2018
 */
public class FlightReservationExtendedDTO extends FlightReservationDTO {
    private RouteExtendedDTO routeExtendedDTOList;

    public FlightReservationExtendedDTO() {
    }

    public FlightReservationExtendedDTO(RouteExtendedDTO routeExtendedDTOList) {
        this.routeExtendedDTOList = routeExtendedDTOList;
    }

    public RouteExtendedDTO getRouteExtendedDTOList() {
        return routeExtendedDTOList;
    }

    public void setRouteExtendedDTOList(RouteExtendedDTO routeExtendedDTOList) {
        this.routeExtendedDTOList = routeExtendedDTOList;
    }
}
