package org.sergei.flightservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.sergei.flightservice.model.Aircraft;

/**
 * @author Sergei Visotsky, 2018
 */
@JsonRootName("route")
public class RouteExtendedDTO extends RouteDTO {

    @JsonProperty("aircraft")
    private Aircraft aircraft;

    @JsonIgnore
    @Override
    public Long getAircraftId() {
        return super.getAircraftId();
    }

    public RouteExtendedDTO() {
    }

    public RouteExtendedDTO(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
}
