package org.sergei.flightservice.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Reservation", description = "Flight reservation meta data model")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO extends ResourceSupport {
    private Long reservationId;
    private Long customerId;
    private Long routeId;
    private LocalDateTime reservationDate;
}
