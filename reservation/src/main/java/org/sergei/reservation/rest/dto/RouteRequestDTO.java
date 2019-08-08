package org.sergei.reservation.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 * @deprecated Should be removed after new business logic adoption
 */
@Getter
@Setter
@NoArgsConstructor
@Deprecated
public class RouteRequestDTO {
    private Long routeId;
    private Double distance;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal price;
    private String place;
    private Long aircraftId;
}
