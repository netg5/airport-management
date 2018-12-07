package org.sergei.flightservice.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "FlightReservation", description = "Flight reservation meta data model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightReservationDTO extends ResourceSupport {
    private Long reservationId;
    private Long customerId;
    private Long routeId;
    private LocalDateTime reservationDate;
}
