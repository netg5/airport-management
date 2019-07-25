package org.sergei.reservation.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sergei Visotsky
 */
@Getter
@Setter
@NoArgsConstructor
public class RouteUpdateRequestDTO {
    private Long routeId;
    private RouteRequestDTO routeRequest;
}
