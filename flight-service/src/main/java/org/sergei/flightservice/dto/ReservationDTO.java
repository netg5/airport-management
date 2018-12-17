package org.sergei.flightservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("Reservation ID")
    private Long reservationId;

    @ApiModelProperty("Customer ID who made reservation")
    private Long customerId;

    @ApiModelProperty("Reserved route ID")
    private Long routeId;

    @ApiModelProperty("Flight reservation date")
    private LocalDateTime reservationDate;
}
