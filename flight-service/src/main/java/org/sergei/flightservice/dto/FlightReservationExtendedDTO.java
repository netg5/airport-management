package org.sergei.flightservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author Sergei Visotsky, 2018
 */
@JsonRootName("flightReservation")
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
