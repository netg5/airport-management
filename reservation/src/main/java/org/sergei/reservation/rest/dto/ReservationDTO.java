package org.sergei.reservation.rest.dto;

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
public class ReservationDTO implements Serializable {
    private static final long serialVersionUID = -128622388504108631L;

    private LocalDateTime dateOfFlying;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer hoursFlying;
    private PassengerDTO passenger;
    private RouteDTO route;
}
