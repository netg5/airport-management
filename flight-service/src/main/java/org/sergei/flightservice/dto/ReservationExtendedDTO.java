package org.sergei.flightservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Sergei Visotsky, 2018
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("flightReservation")
@JsonIgnoreProperties("routeId")
public class ReservationExtendedDTO extends ReservationDTO {

    @JsonProperty("allReservedRoutes")
    private RouteExtendedDTO routeExtendedDTO;
}
