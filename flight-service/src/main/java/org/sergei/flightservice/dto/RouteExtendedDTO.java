package org.sergei.flightservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author Sergei Visotsky, 2018
 */
@JsonRootName("route")
public class RouteExtendedDTO extends RouteDTO {

    @JsonProperty("aircraft")
    private AircraftDTO aircraftDTO;

    @JsonIgnore
    @Override
    public Long getAircraftId() {
        return super.getAircraftId();
    }

    public RouteExtendedDTO() {
    }

    public RouteExtendedDTO(AircraftDTO aircraftDTO) {
        this.aircraftDTO = aircraftDTO;
    }

    public AircraftDTO getAircraftDTO() {
        return aircraftDTO;
    }

    public void setAircraftDTO(AircraftDTO aircraftDTO) {
        this.aircraftDTO = aircraftDTO;
    }
}
