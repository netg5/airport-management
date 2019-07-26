package org.sergei.reports.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Reservation", description = "Reservation made")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    @ApiModelProperty("Reservation ID")
    private Long reservationId;

    @ApiModelProperty("Date when reservation was made")
    private LocalDateTime reservationDate;

    @ApiModelProperty("Customer ID who made reservation")
    private Long customerId;

    @ApiModelProperty("Destination route I")
    private Long routeId;

}
