package org.sergei.flightservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sergei.flightservice.model.Aircraft;

/**
 * @author Sergei Visotsky, 2018
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("route")
public class RouteExtendedDTO extends RouteDTO {

    @JsonProperty("aircraft")
    private AircraftDTO aircraftDTO;
}
