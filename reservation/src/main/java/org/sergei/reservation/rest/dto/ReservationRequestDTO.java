package org.sergei.reservation.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO implements Serializable {
    private static final long serialVersionUID = -128622388504108631L;

    @ApiModelProperty("Customer ID who made reservation")
    private Long customerId;

    @ApiModelProperty("Reserved route ID")
    private Long routeId;

    @ApiModelProperty("Flight reservation date")
    private LocalDateTime reservationDate;
}
