package org.sergei.flightservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "FlightReservation", description = "Flight reservation meta data model")
public class FlightReservationDTO extends ResourceSupport {
    private Long reservationId;
    private Long customerId;
    private Long routeId;
    private LocalDateTime reservationDate;

    FlightReservationDTO() {
    }

    public FlightReservationDTO(Long reservationId, Long customerId, Long routeId,
                                LocalDateTime reservationDate) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
        this.routeId = routeId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    @ApiModelProperty(hidden = true)
    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }
}
