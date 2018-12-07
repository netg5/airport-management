package org.sergei.flightservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sergei Visotsky, 2018
 */
@JsonRootName("flightReservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightReservationExtendedDTO extends FlightReservationDTO {

    @JsonProperty("allReservedRoutes")
    private RouteExtendedDTO routeExtendedDTO;
}
